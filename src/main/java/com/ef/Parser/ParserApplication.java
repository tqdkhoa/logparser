package com.ef.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.ef.Parser.model.HourlyHistory;
import com.ef.Parser.model.LogInfo;
import com.ef.Parser.service.LogInfoService;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ParserApplication.class);

	@Autowired
	private Environment env;

	@Autowired
	private LogInfoService service;

	public static void main(String[] args) {
		SpringApplication.run(ParserApplication.class, args);
	}

	@Override
	public void run(String... args)  throws Exception {
		log.info("StartApplication...");

		if (args.length != 4) {
			System.out.println("Missing input arguments: --accesslog=/path/to/file --startDate=<yyyy-MM-dd.HH:mm:ss> --duration=<hourly|daily> --threshold=<number>");
			return;
		}
		
		String accessLogPath = env.getProperty("accesslog");
		String strStartDate = env.getProperty("startDate");
		String strDuration = env.getProperty("duration");
		String thresholdInput= env.getProperty("threshold");
		
		Date startDate = null, endDate = null;
		Long startDateinMills = null, endDateinMills = null;
		
		//startDate and duration
		String inputPattern = "yyyy-MM-dd.HH:mm:ss";
		SimpleDateFormat inputSdf = new SimpleDateFormat(inputPattern);
		startDate = inputSdf.parse(strStartDate);
		strStartDate = inputSdf.format(startDate);
		startDateinMills = startDate.getTime();
		if("hourly".equalsIgnoreCase(strDuration)) {
			endDateinMills = startDateinMills + (3600 * 1000);
		} else if ("daily".equalsIgnoreCase(strDuration)) {
			endDateinMills = startDateinMills + (24 * 3600 * 1000);
		} else {
			System.out.println("Invalid duration, only accept hourly or daily");
			return;
		}
		endDate = new Date(endDateinMills);
		//Threshold
		Long threshold = Long.valueOf(thresholdInput);
		
		//Read file and filter which lines satisfy the startDate and duration
		List<LogInfo> lstLogInfo = new ArrayList<LogInfo>();
		try (BufferedReader reader = new BufferedReader(new FileReader(accessLogPath))) {

			String line = null;
			while ((line = reader.readLine()) != null) {

				String[] arr = line.split("\\|");
				String dateStr = arr[0];
				String ipAddress = arr[1];
				String request = arr[2];
				String httpStatus = arr[3];
				String userAgent = arr[4];

				String logPattern = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat logSpf = new SimpleDateFormat(logPattern);
				Date logDate = logSpf.parse(dateStr);
				long logTime = logDate.getTime();
				if (logTime >= startDateinMills && logTime <= endDateinMills) {
					lstLogInfo.add(new LogInfo(logDate, ipAddress, request, httpStatus, userAgent));
				}
			}
		}
		System.out.println("Number of requests have been taken in given time:" + lstLogInfo.size());
		System.out.println("Inserting into MySQL ...");
		service.saveAllLogInfo(lstLogInfo);

		List<LogInfo> lst = service.findIPAdressReachingThreshold(startDate, endDate, threshold);
		for (LogInfo inf : lst) {
			String msg = String.format("%s has %d or more requests between %s and %s", inf.getIPAddress(), threshold,
					inputSdf.format(startDate), inputSdf.format(endDate));
			service.saveHourlyHistory(new HourlyHistory(msg));
			System.out.println(msg);
		}
	}
}

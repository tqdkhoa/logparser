package com.ef.Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
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

import com.ef.Parser.model.HourlyHistory;
import com.ef.Parser.model.LogInfo;
import com.ef.Parser.service.LogInfoService;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ParserApplication.class);

	@Autowired
	private LogInfoService service;

	public static void main(String[] args) {
		SpringApplication.run(ParserApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("StartApplication...");
		
//		if(args.length != 3) {
//			System.out.println("Missing input arguments: --startDate= --duration --threshold");
//		}
//		
//		String startDate = args[0];
//		String duration = args[1];
//		Integer threshold = Integer.valueOf(args[2]);

		// Parse Input Arguments
		String inputPattern = "yyyy-MM-dd.HH:mm:ss";
		SimpleDateFormat inputSdf = new SimpleDateFormat(inputPattern);
		Date startDate = null;
		try {
			startDate = inputSdf.parse("2017-01-01.00:00:12");
		} catch (ParseException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		long startTime = startDate.getTime();
		long endTime = startTime + (3600 * 1000);
		Date endDate = new Date(endTime);

		List<LogInfo> lstLogInfo = new ArrayList<LogInfo>();
		// Read log file
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/access.log"))) {

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
				if(logTime >= startTime && logTime <= endTime) {
					lstLogInfo.add(new LogInfo(logDate, ipAddress, request, httpStatus, userAgent));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Number of requests have been taken in given time:" + lstLogInfo.size());
		System.out.println("Inserting into MySQL ...");
		service.saveAllLogInfo(lstLogInfo);
		
		Long threshold = (long) 100;  
		String strStartDate = inputSdf.format(startDate);  
		String strEndDate = inputSdf.format(endDate);
		List<LogInfo> lst = service.findIPAdressReachingThreshold(startDate, endDate, threshold);
		for(LogInfo inf : lst) {
			String msg = String.format("%s has %d or more requests between %s and %s",inf.getIPAddress(), threshold, strStartDate, strEndDate);
			service.saveHourlyHistory(new HourlyHistory(msg));
			System.out.println(msg);
		}
	}
}

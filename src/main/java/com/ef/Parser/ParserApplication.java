package com.ef.Parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import org.w3c.dom.ls.LSResourceResolver;

import ch.qos.logback.core.rolling.helper.TimeBasedArchiveRemover.ArhiveRemoverRunnable;

@SpringBootApplication
public class ParserApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ParserApplication.class);

	@Autowired
	private LogInfoRepository repository;

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
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = simpleDateFormat.parse("2017-01-01 00:00:12");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		long startTime = date.getTime();
		long endTime = startTime + (3600 * 1000);

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
				
				Date logDate = simpleDateFormat.parse(dateStr);
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
		repository.saveAll(lstLogInfo);
	}
}

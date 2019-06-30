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

import com.mysql.cj.log.Log;

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

		StringBuilder content = new StringBuilder();
		content.append("id,date,http_status,ipaddress,request,user_agent");
		content.append("\n");
		// Read log file
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/access.log"))) {

			String line;
			Integer id = 1;
			//List<LogInfo> lst = new ArrayList<>();
			
			while ((line = reader.readLine()) != null) {
				// System.out.println(content.toString());
				String[] arr = line.split("\\|");

				String dateStr = arr[0];
				String pattern = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date date = simpleDateFormat.parse(dateStr);
				String ipAddress = arr[1];
				String request = arr[2];
				String httpStatus = arr[3];
				String userAgent = arr[4];
				
				content.append(String.format("%d,%s,%s,%s,%s,%s", id++,dateStr,httpStatus,ipAddress,request,userAgent));
				content.append("\n");
				// repository.save(new LogInfo(date, ipAddress, request, httpStatus,// userAgent));
				// lst.add(new LogInfo(date, ipAddress, request, httpStatus, userAgent));
				
			}
			//repository.saveAll(lst);
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
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/access.csv"))){
			bw.write(content.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Store in MySQL

		// ToDo
	}
}

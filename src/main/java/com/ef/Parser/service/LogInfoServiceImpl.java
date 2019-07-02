package com.ef.Parser.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ef.Parser.model.DailyHistory;
import com.ef.Parser.model.HourlyHistory;
import com.ef.Parser.model.LogInfo;
import com.ef.Parser.repository.DailyHistoryRepository;
import com.ef.Parser.repository.HourlyHistoryRepository;
import com.ef.Parser.repository.LogInfoRepository;

@Service
@Transactional
public class LogInfoServiceImpl implements LogInfoService {

	@Autowired
	private LogInfoRepository logInfrepository;
	
	@Autowired
	private HourlyHistoryRepository hourlyHistrepository;
	
	@Autowired
	private DailyHistoryRepository dailyHistrepository;

	@Override
	public void saveLogInfo(LogInfo logInfo) {
		logInfrepository.save(logInfo);
	}

	@Override
	public void saveAllLogInfo(List<LogInfo> lstLogInfo) {
		logInfrepository.saveAll(lstLogInfo);
	}

	@Override
	public List<LogInfo> findIPAdressReachingThreshold(Date startDate, Date endDate, Long threshold) {
		return logInfrepository.findIPAdressReachingThreshold(startDate, endDate, threshold);
	}

	@Override
	public void saveHourlyHistory(HourlyHistory hourlyHist) {
		hourlyHistrepository.save(hourlyHist);
	}

	@Override
	public void saveDailyHistory(DailyHistory dailyHist) {
		dailyHistrepository.save(dailyHist);
	}

}

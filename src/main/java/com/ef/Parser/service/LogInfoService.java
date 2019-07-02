package com.ef.Parser.service;

import java.util.Date;
import java.util.List;

import com.ef.Parser.model.DailyHistory;
import com.ef.Parser.model.HourlyHistory;
import com.ef.Parser.model.LogInfo;

public interface LogInfoService {

	public void saveLogInfo(LogInfo logInfo);
	
	public void saveAllLogInfo(List<LogInfo> lstLogInfo);
	
	public List<LogInfo> findIPAdressReachingThreshold(Date startDate, Date endDate, Long threshold);
	
	public void saveHourlyHistory(HourlyHistory hourlyHist);
	
	public void saveDailyHistory(DailyHistory dailyHist);
}

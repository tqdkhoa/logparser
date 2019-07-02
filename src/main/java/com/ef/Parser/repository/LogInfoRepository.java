package com.ef.Parser.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ef.Parser.model.LogInfo;

@Repository
public interface LogInfoRepository extends CrudRepository<LogInfo, Long> {

	@Query(value = "From LogInfo inf Where inf.logDate >= :startDate And inf.logDate <= :endDate Group By iPAddress Having Count(*) >= :threshold")
	public List<LogInfo> findIPAdressReachingThreshold(
			@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, 
			@Param("threshold") Long threshold);
}

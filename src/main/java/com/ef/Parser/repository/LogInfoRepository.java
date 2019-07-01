package com.ef.Parser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ef.Parser.model.LogInfo;

@Repository
public interface LogInfoRepository extends CrudRepository<LogInfo, Long> {
	
}

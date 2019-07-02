package com.ef.Parser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ef.Parser.model.HourlyHistory;

@Repository
public interface HourlyHistoryRepository extends CrudRepository<HourlyHistory, Long>{

}

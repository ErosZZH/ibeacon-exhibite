package com.yzlpie.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.yzlpie.jpa.domain.FootPrint;

public interface FootPrintRepository extends JpaSpecificationExecutor<FootPrint>, JpaRepository<FootPrint, String>{
	    	    
	@Query(value="select * from t_footprint where userid = ?1 order by entertime desc limit 10", nativeQuery = true) 
	public List<FootPrint> getFootPrintByUserId(String userId);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE t_footprint SET exittime = now() WHERE footprintid = ?1", nativeQuery = true) 
	public void updateExitTime(String fpId);
}
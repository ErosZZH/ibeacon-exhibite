package com.yzlpie.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.yzlpie.jpa.domain.User;

public interface UserRepository extends JpaSpecificationExecutor<User>, JpaRepository<User, String>{
	    	    
	@Query(value="select userid from t_user where deviceToken = ?1", nativeQuery = true) 
	public String getTokenFromDeviceToken(String deviceToken);
	
	@Query(value="select count(*) from t_user where deviceToken = ?1", nativeQuery = true) 
	public int deviceTokenCount(String deviceToken);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE t_user SET deviceToken = ?2 WHERE deviceToken = ?1", nativeQuery = true) 
	public void updateUser(String oldToken, String newToken);
}
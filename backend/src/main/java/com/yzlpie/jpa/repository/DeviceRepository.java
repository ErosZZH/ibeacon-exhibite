package com.yzlpie.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yzlpie.jpa.domain.BeaconDevice;

public interface DeviceRepository extends JpaSpecificationExecutor<BeaconDevice>, JpaRepository<BeaconDevice, String>{
	    	    
	@Query(value="select * from t_device where deviceid = ?1", nativeQuery = true) 
	public List<BeaconDevice> getDeviceById(String deviceId);
	
	@Query(value="select count(*) from t_device where deviceid = ?1", nativeQuery = true) 
	public int countDeviceByDeviceId(String deviceId);
}
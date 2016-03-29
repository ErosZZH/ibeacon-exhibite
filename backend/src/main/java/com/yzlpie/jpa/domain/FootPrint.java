package com.yzlpie.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_footprint")
public class FootPrint {

	@Id
	@Column(name="footprintid",unique=true,nullable=false,length=45)
	private String footPrintId;
	
	@Column(name="deviceid",nullable=false,length=45)
	private String deviceId;
	
	@Column(name="userid",nullable=false,length=45)
	private String userId;
	
	@Column(name="entertime",nullable=false)
	private Date enterTime;
	
	@Column(name="exittime")
	private Date exitTime;

	public String getFootPrintId() {
		return footPrintId;
	}

	public void setFootPrintId(String footPrintId) {
		this.footPrintId = footPrintId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}
	
	
	

}

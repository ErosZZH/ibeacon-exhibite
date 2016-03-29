package com.yzlpie.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user")
public class User {

	@Id
	@Column(name="userid",unique=true,nullable=false,length=45)
	private String userId;
	
	@Column(name="username",length=45,nullable=false,unique=true)
	private String username;
	
	@Column(name="password",length=45,nullable=false)
	private String password;
	
	@Column(name="createtime",nullable=false)
	private Date createTime;
	
	@Column(name="status",scale=10,nullable=false)
	private int status;
	
	@Column(name="deviceToken",length=80)
	private String deviceToken;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
	
}


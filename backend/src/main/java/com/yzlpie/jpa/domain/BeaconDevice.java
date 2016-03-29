package com.yzlpie.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_device")
public class BeaconDevice {

	@Id
	@Column(name="deviceid",unique=true,nullable=false,length=45)
	private String deviceId;
	
	@Column(name="url",length=128)
	private String url;
	
	@Column(name="image",length=128)
	private String image;
	
	@Column(name="title",length=256)
	private String title;
	
	@Column(name="detail",length=2000)
	private String detail;
	

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
}

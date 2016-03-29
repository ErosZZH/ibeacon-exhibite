package com.yzlpie.vo;

public class FailVO extends ResultVO{

	private String message;
	private String trackID;
	
	public FailVO(int code, String status, String message, String trackID) {
		super(code, status);
		this.message = message;
		this.trackID = trackID;
	}

	public String getMessage() {
		return message;
	}

	public String getTrackID() {
		return trackID;
	}
	
	
}

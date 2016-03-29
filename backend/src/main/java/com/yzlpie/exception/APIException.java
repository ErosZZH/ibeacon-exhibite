package com.yzlpie.exception;

public class APIException extends Exception {

	private int errorCode;
	private String trackId;

	public APIException(String message) {
		super(message);
	}

	public APIException(String message, int errorCode, String trackId) {
		super(message);
		this.errorCode = errorCode;
		this.trackId = trackId;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
	public String getTrackId() {
		return trackId;
	}
}

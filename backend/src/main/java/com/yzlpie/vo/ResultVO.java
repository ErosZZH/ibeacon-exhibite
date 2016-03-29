package com.yzlpie.vo;

public abstract class ResultVO {

	private int code;
	private String status;
	
	public ResultVO(int code, String status) {
		this.code = code;
		this.status = status;
	}
	
	public int getCode() {
		return code;
	}
	public String getStatus() {
		return status;
	}
	
	
}

package com.yzlpie.service;


public interface UserService {

	String getTokenFromDeviceToken(String deviceToken);
	String registerUser(String deviceToken);
	void updateUser(String oldToken, String newToken);
}

package com.yzlpie.common;

public class EncryptUtil {

	/*
	 *	Encrypt userid to user token 
	 */
	public static String encryptUserId(String userId) {
		return userId + "_token";
	}
	
	/*
	 * 	Decrypt user token to userId
	 */
	public static String decryptUserToken(String userToken) {
		return userToken.substring(0, userToken.length() - "_token".length());
	}
}

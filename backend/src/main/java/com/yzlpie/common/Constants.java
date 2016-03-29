package com.yzlpie.common;

import java.io.InputStream;
import java.util.Properties;

public class Constants {

	public static String domainName = Constants.getProperties().getProperty("api.domainName");;


	public static Properties getProperties() {
		InputStream in = Constants.class.getClassLoader().getResourceAsStream("config/props/api.properties");
		Properties config = new Properties();
		try {
			config.load(in);
		} catch (Exception e) {
		}
		return config;
	}
}

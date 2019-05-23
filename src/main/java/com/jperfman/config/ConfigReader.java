package com.jperfman.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	private Properties props;
	
	public ConfigReader(String configPath, String configName) { 

		props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(configPath + File.separator
					+ configName));
			props.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getValue(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

}

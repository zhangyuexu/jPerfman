package com.jperfman.config;

public abstract class Config {
	protected ConfigReader configReader;
	
	public void init(String configPath, String configName) {
		configReader = new ConfigReader(configPath, configName);
	}
	
	abstract void load();
}

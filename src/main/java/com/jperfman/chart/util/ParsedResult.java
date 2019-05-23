package com.jperfman.chart.util;

import java.util.Map;

public class ParsedResult {
	
	private String jperfGroupName;
	
	private double elapsed;
	
	private long epoch;
	
	private double scriptRunTime;
	
	private Map<String,Double> customTimer;			//类型有待确定，是否使用复合类型
	
	private String error;
	
	public ParsedResult(String jperfGroupName, double elapsed, long epoch,
			double scriptRunTime, Map<String,Double> customTimer, String error) {
		this.jperfGroupName = jperfGroupName;
		this.elapsed = elapsed;
		this.epoch = epoch;
		this.scriptRunTime = scriptRunTime;
		this.error = error;
		this.customTimer = customTimer;
	}
	
	public double getElapsed() {
		return elapsed;
	}
	
	public long getEpoch() {
		return epoch;
	}
	
	public double getScriptRunTime() {
		return scriptRunTime;
	}
	
	public Map<String,Double> getCustomTimer() {
		return customTimer;
	}
	
	public String getError() {
		return error;
	}
}

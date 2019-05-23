package com.jperfman.result.analyzer;

public class BaseAnalyzer {
	protected String filename;
	protected int runTime;
	protected int interval;
	
	protected double totalCnt;
	
	public BaseAnalyzer() {
		
	}
	
	public BaseAnalyzer(String filename, int runTime, int interval) {
		this.filename = filename;
		this.runTime = runTime;
		this.interval = interval;
		this.totalCnt = 0;
	}
}

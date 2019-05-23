package com.jperfman.result;

import java.util.Map;

public class Result {
	private String jperfGroupName;
	
	private double elapsed;
	
	private long epoch;
	
	private double scriptRunTime;
	
	private String customTimer;			//类型有待确定，是否使用复合类型
	
	private String error;
	
	public Result(String jperfGroupName, long elapsed, long epoch,
			long scriptRunTime, Map customTimer, String error) {
		this.jperfGroupName = jperfGroupName;
		this.elapsed = (double)elapsed / 1000;
		this.epoch = epoch;
		this.scriptRunTime = (double)scriptRunTime / 1000;
		this.customTimer = customTimer.toString();
		this.error = error;
	}
	
	public long getEpoch() {
		return epoch;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(elapsed);
		sb.append(",");
		sb.append(epoch);
		sb.append(",");
		sb.append(jperfGroupName);
		sb.append(",");
		sb.append(scriptRunTime);
		sb.append(",");
		sb.append(error);
		sb.append(",");
		sb.append(customTimer);
		sb.append("\n");
		
		return sb.toString();
	}
}

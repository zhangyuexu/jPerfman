package com.jperfman.monitor;

import com.jperfman.exception.MonitorException;

public interface IMonitor {
	public String monitor() throws MonitorException;
	
	public String getHost();
	
	public String getPort();
}

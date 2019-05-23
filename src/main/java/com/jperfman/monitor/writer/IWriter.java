package com.jperfman.monitor.writer;

import com.jperfman.monitor.MonitorInfoEntity;

public interface IWriter {
	public void writeInfo(MonitorInfoEntity monitorInfo);
}

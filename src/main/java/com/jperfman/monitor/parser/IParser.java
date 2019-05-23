package com.jperfman.monitor.parser;

import com.jperfman.monitor.MonitorInfoEntity;

public interface IParser {
	public MonitorInfoEntity parse(String info);
}

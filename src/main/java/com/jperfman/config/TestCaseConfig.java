package com.jperfman.config;

import com.jperfman.core.TestCaseInfo;

public class TestCaseConfig extends Config {
	private TestCaseInfo testCaseInfo;
	
	public TestCaseConfig(String configPath, String configName) {
		testCaseInfo = new TestCaseInfo();
		super.init(configPath, configName);
	}

	@Override
	public void load() {
		// TODO 把从配置文件中读取的数值，各个set到testcaseinfo实体中
		testCaseInfo.setRuntime(Integer.valueOf(configReader
				.getValue("jperfman.testcase.runtime", "600000")));
		testCaseInfo.setRampupMode(Integer.valueOf(configReader
				.getValue("jperfman.testcase.ramupmode", "1")));
		testCaseInfo.setRampup(Integer.valueOf(configReader
				.getValue("jperfman.testcase.rampup", "30000")));
		testCaseInfo.setThreadInitNum(Integer.valueOf(configReader
				.getValue("jperfman.testcase.thread.initnum", "5")));
		testCaseInfo.setThreadIncrement(Integer.valueOf(configReader
				.getValue("jperfman.testcase.thread.increment", "5")));
		testCaseInfo.setThreadDuration(Integer.valueOf(configReader
				.getValue("jperfman.testcase.thread.duration", "60000")));
		testCaseInfo.setThreadTotal(Integer.valueOf(configReader
				.getValue("jperfman.testcase.thread.total", "20")));
		testCaseInfo.setScript(configReader
				.getValue("jperfman.testcase.script", ""));
		testCaseInfo.setScriptType(Integer.valueOf(configReader.getValue(
				"jperfman.testcase.script.type", "0")));
		testCaseInfo.setMonitorIP(configReader.getValue(
				"jperfman.testcase.monitor.ip", ""));
		testCaseInfo.setMonitorPort(configReader.getValue(
				"jperfman.testcase.monitor.port", ""));
	}

	public TestCaseInfo getTestCaseInfo() {
		return testCaseInfo;
	}
}

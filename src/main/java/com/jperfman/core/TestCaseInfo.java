package com.jperfman.core;

public class TestCaseInfo {

	private int runtime;			// 压测整体执行的时间， 单位秒
	
	private int rampupMode;			// 分为线性和梯度模式
	
	private int rampup;				// 线性模式时，此值用来衡量爬坡时间长短
	
	private int threadInitNum;		// 压测开始阶段，初始化启动线程数
	
	private int threadIncrement;	// 每一个梯度，线程增加的数量
	
	private int threadDuration;		// 每个梯度
	
	private int threadTotal;		// 最高线程数时，线程的数量
	
	private String script;			// 需要执行的测试class或jar文件所在路径
	
	private int scriptType;			// 分为class和jar包两种类型，0表示class, 1表示jar
	
	private int taskMode;
	
	private int processNum;
	
	private String monitorIP;		// 部署监控程序的被压测机器的IP
	
	private String monitorPort;		// 部署监控程序的服务的端口
	
	public TestCaseInfo() {
		
	}
	
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	
	public int getRuntime() {
		return runtime;
	}
	
	public void setRampupMode(int rampupMode) {
		this.rampupMode = rampupMode;
	}
	
	public int getRampupMode() {
		return rampupMode;
	}
	
	public void setRampup(int rampup) {
		this.rampup = rampup;
	}
	
	public int getRampup() {
		return rampup;
	}
	
	public void setThreadInitNum(int threadInitNum) {
		this.threadInitNum = threadInitNum;
	}
	
	public int getThreadInitNum() {
		return threadInitNum;
	}
	
	public void setThreadIncrement(int threadIncrement) {
		this.threadIncrement = threadIncrement;
	}
	
	public int getThreadIncrement() {
		return threadIncrement;
	}
	
	public void setThreadDuration(int threadDuration) {
		this.threadDuration = threadDuration;
	}
	
	public int getThreadDuration() {
		return threadDuration;
	}
	
	public void setThreadTotal(int threadTotal) {
		this.threadTotal = threadTotal;
	}
	
	public int getThreadTotal() {
		return threadTotal;
	}
	
	public void setScript(String script) {
		this.script = script;
	}
	
	public String getScript() {
		return script;
	}
	
	public void setScriptType(int type) {
		this.scriptType = type;
	}
	
	public int getScriptType() {
		return scriptType;
	}
	
	public void setTaskMode(int taskMode) {
		this.taskMode = taskMode;
	}
	
	public int getTaskMode() {
		return taskMode;
	}
	
	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}
	
	public int getProcessNum() {
		return processNum;
	}
	
	public void setMonitorIP(String monitorIP) {
		this.monitorIP = monitorIP;
	}
	
	public String getMonitorIP() {
		return monitorIP;
	}
	
	public void setMonitorPort(String monitorPort) {
		this.monitorPort = monitorPort;
	}
	
	public String getMonitorPort() {
		return monitorPort;
	}
}

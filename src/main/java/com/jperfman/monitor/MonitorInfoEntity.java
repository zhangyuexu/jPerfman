package com.jperfman.monitor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitorInfoEntity {
	private String loadAvg;
	private String loadAvg5m;
	private String loadAvg15m;
	
	private String memFree;
	private String memBuff;
	private String memCach;
	
	private String cpuUse;
	private String cpuSys;
	private String cpuIdl;
	
	private String iowait;
	private String swapSi;
	private String swapSo;
	
	private String ioBi;
	private String ioBo;
	
	private String netRcv;
	private String netSnd;
	
	public void setLoadAvg(String loadAvg) {
		this.loadAvg = loadAvg;
	}
	
	public String getLoadAvg() {
		return loadAvg;
	}
	
	public void setLoadAvg5m(String loadAvg) {
		this.loadAvg5m = loadAvg;
	}
	
	public String getLoadAvg5m() {
		return loadAvg5m;
	}
	
	public void setLoadAvg15m(String loadAvg) {
		this.loadAvg15m = loadAvg;
	}
	
	public String getLoadAvg15m() {
		return loadAvg15m;
	}
	
	public void setMemFree(String memFree) {
		this.memFree = memFree;
	}
	
	public String getMemFree() {
		return memFree;
	}
	
	public void setMemBuff(String memBuff) {
		this.memBuff = memBuff;
	}
	
	public String getMemBuff() {
		return memBuff;
	}
	
	public void setMemCach(String memCach) {
		this.memCach = memCach;
	}
	
	public String getMemCach() {
		return memCach;
	}
	
	public void setCpuUse(String cpuUse) {
		this.cpuUse = cpuUse;
	}
	
	public String getCpuUse() {
		return cpuUse;
	}
	
	public void setCpuSys(String cpuSys) {
		this.cpuSys = cpuSys;
	}
	
	public String getCpuSys() {
		return cpuSys;
	}
	
	public void setCpuIdl(String cpuIdl) {
		this.cpuIdl = cpuIdl;
	}
	
	public String getCpuIdl() {
		return cpuIdl;
	}
	
	public void setIoWait(String ioWait) {
		this.iowait = ioWait;
	}
	
	public String getIoWait() {
		return iowait;
	}
	
	public void setSwapSi(String swapSi) {
		this.swapSi = swapSi;
	}
	
	public String getSwapSi() {
		return swapSi;
	}
	
	public void setSwapSo(String swapSo) {
		this.swapSo = swapSo;
	}
	
	public String getSwapSo() {
		return swapSo;
	}
	
	public void setIoBi(String ioBi) {
		this.ioBi = ioBi;
	}
	
	public String getIoBi() {
		return ioBi;
	}
	
	public void setIoBo(String ioBo) {
		this.ioBo = ioBo;
	}
	
	public String getIoBo() {
		return ioBo;
	}
	
	public void setNetRcv(String netRcv) {
		this.netRcv = netRcv;
	}
	
	public String getNetRcv() {
		return netRcv;
	}
	
	public void setNetSnd(String netSnd) {
		this.netSnd = netSnd;
	}
	
	public String getNetSnd() {
		return netSnd;
	}
	
	public String toString() {
		StringBuffer buff = new StringBuffer();
		
		buff.append("loadAvg: ")
			.append(this.loadAvg)
			.append("memFree: ")
			.append(memFree)
			.append("memBuff: ")
			.append(memBuff)
			.append("memCache: ")
			.append(memCach)
			.append("cpuUse: ")
			.append(cpuUse)
			.append("cpuSys: ")
			.append(cpuSys)
			.append("cpuIdl: ")
			.append(cpuIdl)
			.append("ioWait: ")
			.append(iowait)
			.append("swapSi: ")
			.append(swapSi)
			.append("swapSo: ")
			.append(swapSo)
			.append("ioBi: ")
			.append(ioBi)
			.append("ioBo: ")
			.append(ioBo)
			.append("netRcv: ")
			.append(netRcv)
			.append("netSnd: ")
			.append(netSnd);
			
		
		return buff.toString();
	}
	
	public String toBackUpLog() {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		sb.append(df.format(new Date()))
			.append("\t")
			.append("use:")
			.append(cpuUse)
			.append("\t")
			.append("sys:")
			.append(cpuSys)
			.append("\t")
			.append("idl:")
			.append(cpuIdl)
			.append("\t")
			.append("free:")
			.append(memFree)
			.append("\t")
			.append("buff:")
			.append(memBuff)
			.append("\t")
			.append("cache:")
			.append(memCach)
			.append("\t")
			.append("iowait:")
			.append(iowait)
			.append("\t")
			.append("swap_si:")
			.append(swapSi)
			.append("\t")
			.append("swap_so:")
			.append(swapSo)
			.append("\t")
			.append("io_bi:")
			.append(ioBi)
			.append("\t")
			.append("io_bo:")
			.append(ioBo)
			.append("\t")
			.append("load:")
			.append(loadAvg)
			.append("\n");
		
		return sb.toString();
	}
	
	public String toAllLog() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("time:")
			.append(System.currentTimeMillis())
			.append(",")
			.append("use:")
			.append(cpuUse)
			.append(",")
			.append("sys:")
			.append(cpuSys)
			.append(",")
			.append("idl:")
			.append(cpuIdl)
			.append(",")
			.append("free:")
			.append(memFree)
			.append(",")
			.append("buff:")
			.append(memBuff)
			.append(",")
			.append("cache:")
			.append(memCach)
			.append(",")
			.append("iowait:")
			.append(iowait)
			.append(",")
			.append("swap_si:")
			.append(swapSi)
			.append(",")
			.append("swap_so:")
			.append(swapSo)
			.append(",")
			.append("io_bi:")
			.append(ioBi)
			.append(",")
			.append("io_bo:")
			.append(ioBo)
			.append(",")
			.append("load:")
			.append(loadAvg)
			.append(",")
			.append("net_rcv:")
			.append(netRcv)
			.append(",")
			.append("net_snd:")
			.append(netSnd)
			.append("\n");
		
		return sb.toString();
	}
}

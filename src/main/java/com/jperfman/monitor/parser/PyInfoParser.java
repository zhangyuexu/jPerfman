package com.jperfman.monitor.parser;

import com.jperfman.monitor.MonitorInfoEntity;

@Deprecated
public class PyInfoParser implements IParser{

	@Override
	public MonitorInfoEntity parse(String info) {
		// TODO Auto-generated method stub
		String[] lines = info.split("\n");
		
		String resourceLine = lines[3].trim();
		String loadAvgLine = lines[4].trim();
		
		resourceLine = resourceLine.replaceAll("[ ]{1,}", " ");
		
		String[] items = resourceLine.split(" ");
		MonitorInfoEntity monitorInfo = new MonitorInfoEntity();
		
		monitorInfo.setLoadAvg(loadAvgLine);
		monitorInfo.setMemFree(items[3].trim());
		monitorInfo.setMemBuff(items[4].trim());
		monitorInfo.setMemCach(items[5].trim());
		monitorInfo.setCpuUse(items[12].trim());
		monitorInfo.setCpuSys(items[13].trim());
		monitorInfo.setCpuIdl(items[14].trim());
		monitorInfo.setIoWait(items[15].trim());
		monitorInfo.setSwapSi(items[6].trim());
		monitorInfo.setSwapSo(items[7].trim());
		monitorInfo.setIoBi(items[8].trim());
		monitorInfo.setIoBo(items[9].trim());
		
		return monitorInfo;
	}

}

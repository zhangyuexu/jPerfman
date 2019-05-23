package com.jperfman.monitor.parser;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jperfman.monitor.MonitorInfoEntity;

@Deprecated
public class PyJsonParser implements IParser{

	@Override
	public MonitorInfoEntity parse(String info) {
		// TODO Auto-generated method stub
		JSONObject obj = JSONObject.fromObject(info);
		Map<String,String> dataMap = (Map)obj;
		MonitorInfoEntity monitorInfo = new MonitorInfoEntity();
		
		monitorInfo.setLoadAvg(dataMap.get("LoadAvg1M"));
		monitorInfo.setLoadAvg5m(dataMap.get("LoadAvg5M"));
		monitorInfo.setLoadAvg15m(dataMap.get("LoadAvg15M"));
		monitorInfo.setMemFree(dataMap.get("MemFree"));
		monitorInfo.setMemBuff(dataMap.get("MemBuff"));
		monitorInfo.setMemCach(dataMap.get("MemCach"));
		monitorInfo.setCpuUse(dataMap.get("CpuUse"));
		monitorInfo.setCpuSys(dataMap.get("CpuSys"));
		monitorInfo.setCpuIdl(dataMap.get("CpuIdl"));
		monitorInfo.setIoWait(dataMap.get("IOWait"));
		monitorInfo.setSwapSi(dataMap.get("SwapSi"));
		monitorInfo.setSwapSo(dataMap.get("SwapSo"));
		monitorInfo.setIoBi(dataMap.get("IOBi"));
		monitorInfo.setIoBo(dataMap.get("IOBo"));
		monitorInfo.setNetRcv(dataMap.get("NetRcv"));
		monitorInfo.setNetSnd(dataMap.get("NetSnd"));
		
		return monitorInfo;
	}

}

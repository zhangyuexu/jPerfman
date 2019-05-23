package com.jperfman.monitor.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.jperfman.util.Const;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class PyJsonWriter {

	private String outputDir;
	private String outputName;
	
	public PyJsonWriter(String outputDir, String outputName) {
		this.outputDir = outputDir;
		this.outputName = outputName;
	}
	
	public String getOutputFullName() {
		return outputDir + File.separator + outputName;
	}
	
	public void saveMax() {
		File outLog = new File(outputDir + File.separator + Const.MONITOR_MAX_FILE);
		
		StringBuffer sb = new StringBuffer();
		sb.append("CpuUse").append("\t")
			.append("CpuSys").append("\t")
			.append("CpuIdl").append("\t")
			.append("MemFree").append("\t")
			.append("MemBuff").append("\t")
			.append("MemCach").append("\t")
			.append("IOWait").append("\t")
			.append("SwapSi").append("\t")
			.append("SwapSo").append("\t")
			.append("IOBi").append("\t")
			.append("IOBo").append("\t")
			.append("LoadAvg").append("\n");
		
		sb.append(GlobalVal.CpuUse).append("\t")
			.append(GlobalVal.CpuSys).append("\t")
			.append(GlobalVal.CpuIdl).append("\t")
			.append(GlobalVal.MemFree).append("\t")
			.append(GlobalVal.MemBuff).append("\t")
			.append(GlobalVal.MemCach).append("\t")
			.append(GlobalVal.IOWait).append("\t")
			.append(GlobalVal.SwapSi).append("\t")
			.append(GlobalVal.SwapSo).append("\t")
			.append(GlobalVal.IOBi).append("\t")
			.append(GlobalVal.IOBo).append("\t")
			.append(GlobalVal.LoadAvg).append("\n");
		
		try {
			FileOutputStream out = new FileOutputStream(outLog, true);
			out.write((sb.toString()).getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void isMax(String json) {
		JSONObject obj = null;
		try {
			obj = JSONObject.fromObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		Map<String,String> dataMap = (Map)obj;
		
		if (Integer.parseInt(dataMap.get("CpuUse")) > GlobalVal.CpuUse) {
			GlobalVal.CpuUse = Integer.parseInt(dataMap.get("CpuUse"));
		}
		if (Integer.parseInt(dataMap.get("CpuSys")) > GlobalVal.CpuSys) {
			GlobalVal.CpuSys = Integer.parseInt(dataMap.get("CpuSys"));
		}
		if (Integer.parseInt(dataMap.get("CpuIdl")) > GlobalVal.CpuIdl) {
			GlobalVal.CpuIdl = Integer.parseInt(dataMap.get("CpuIdl"));
		}
		if (Integer.parseInt(dataMap.get("MemFree")) > GlobalVal.MemFree) {
			GlobalVal.MemFree = Integer.parseInt(dataMap.get("MemFree"));
		}
		if (Integer.parseInt(dataMap.get("MemBuff")) > GlobalVal.MemBuff) {
			GlobalVal.MemBuff = Integer.parseInt(dataMap.get("MemBuff"));
		}
		if (Integer.parseInt(dataMap.get("MemCach")) > GlobalVal.MemCach) {
			GlobalVal.MemCach = Integer.parseInt(dataMap.get("MemCach"));
		}
		if (Integer.parseInt(dataMap.get("IOBi")) > GlobalVal.IOBi) {
			GlobalVal.IOBi = Integer.parseInt(dataMap.get("IOBi"));
		}
		if (Integer.parseInt(dataMap.get("IOBo")) > GlobalVal.IOBo) {
			GlobalVal.IOBo = Integer.parseInt(dataMap.get("IOBo"));
		}
		if (Integer.parseInt(dataMap.get("SwapSi")) > GlobalVal.SwapSi) {
			GlobalVal.SwapSi = Integer.parseInt(dataMap.get("SwapSi"));
		}
		if (Integer.parseInt(dataMap.get("SwapSo")) > GlobalVal.SwapSo) {
			GlobalVal.SwapSo = Integer.parseInt(dataMap.get("SwapSo"));
		}
		if (Integer.parseInt(dataMap.get("IOWait")) > GlobalVal.IOWait) {
			GlobalVal.IOWait = Integer.parseInt(dataMap.get("IOWait"));
		}
		if (Double.parseDouble(dataMap.get("LoadAvg1M")) > GlobalVal.LoadAvg) {
			GlobalVal.LoadAvg = Double.parseDouble(dataMap.get("LoadAvg1M"));
		}
	}
	
	public void writeInfo(String monitorInfo) {
		// TODO Auto-generated method stub
		if (monitorInfo == null) {
			return;
		}
//		File backupLog = new File(outputDir + File.separator + BACKLOG);
//		File allLog = new File(outputDir + File.separator + ALLLOG);
		File outLog = new File(outputDir + File.separator + outputName);
		
		isMax(monitorInfo);
		
		try {
//			FileOutputStream outBackup = new FileOutputStream(backupLog, true);
//			outBackup.write(monitorInfo.toBackUpLog().getBytes());
//			outBackup.close();
//			
//			FileOutputStream outAll = new FileOutputStream(allLog, true);
//			outAll.write(monitorInfo.toAllLog().getBytes());
//			outAll.close();
			
			FileOutputStream out = new FileOutputStream(outLog, true);
			out.write((monitorInfo.toString() + "\n").getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

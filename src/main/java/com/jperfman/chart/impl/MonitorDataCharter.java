package com.jperfman.chart.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.jperfman.chart.util.MonDataGraph;



public class MonitorDataCharter extends Thread {

	private String fileName;
	private String fileDir;
	private int runTime;
	
	private MonDataGraph graph;
	
	private static final String imgCpu = "TOPCPU.png";
	private static final String imgMem = "TOPMEM.png";
	private static final String imgIO = "TOPIO.png";
	private static final String imgSwap = "TOPSWAP.png";
	private static final String imgNet = "TOPNET.png";
	
	public MonitorDataCharter(String fileName, String fileDir, int runTime) {
		this.fileName = fileName;
		this.fileDir = fileDir;
		this.runTime = runTime;
	}
	
	public void init() {
		graph = new MonDataGraph();
		graph.initPicSize(runTime);
	}
	
	public void doChart() {
		File file = new File(fileName);
		
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			
			String line = null;
			int i = 1;
			long offset = 0;
			
			Map<Integer,Integer> cpuUse = new TreeMap<Integer,Integer>();
			Map<Integer,Integer> cpuSys = new TreeMap<Integer,Integer>();
			Map<Integer,Double> cpuLoad = new TreeMap<Integer,Double>();
			
			Map<Integer,Integer> memFree = new TreeMap<Integer,Integer>();
			Map<Integer,Integer> memBuff = new TreeMap<Integer,Integer>();
			Map<Integer,Integer> memCache = new TreeMap<Integer,Integer>();
			
			Map<Integer,Integer> ioBi = new TreeMap<Integer,Integer>();
			Map<Integer,Integer> ioBo = new TreeMap<Integer,Integer>();
			
			Map<Integer,Integer> swapBi = new TreeMap<Integer,Integer>();
			Map<Integer,Integer> swapBo = new TreeMap<Integer,Integer>();
			
			Map<Integer,Integer> netRcv = new TreeMap<Integer,Integer>();
			Map<Integer,Integer> netSnd = new TreeMap<Integer,Integer>();
			
			while ((line = in.readLine()) != null) {
//				String[] cols = line.split(",");
//				String time = cols[0].split(":")[1];
				JSONObject obj = JSONObject.fromObject(line);
				Map<String,String> dataMap = (Map)obj;
				if (i == 1) {
					offset = Long.parseLong(dataMap.get("Time"));
				}
				
				//BugFix: 引入四舍五入是为了解决，监控数据第一个时间间隔总是小于预设的interval
				int key = (int)Math.round(((Double.parseDouble(dataMap.get("Time")) - offset) / 1000));
				int use = Integer.parseInt(dataMap.get("CpuUse"));
				int sys = Integer.parseInt(dataMap.get("CpuSys"));
				double load = Double.parseDouble(dataMap.get("LoadAvg1M"));
				
				int free = Integer.parseInt(dataMap.get("MemFree"));
				int buff = Integer.parseInt(dataMap.get("MemBuff"));
				int cache = Integer.parseInt(dataMap.get("MemCach"));
				
				int iobi = Integer.parseInt(dataMap.get("IOBi"));
				int iobo = Integer.parseInt(dataMap.get("IOBo"));
				
				int swapbi = Integer.parseInt(dataMap.get("SwapSi"));
				int swapbo = Integer.parseInt(dataMap.get("SwapSo"));
				
				int netrcv = Integer.parseInt(dataMap.get("NetRcv"));
				int netsnd = Integer.parseInt(dataMap.get("NetSnd"));
				
//				int use = Integer.parseInt(cols[1].split(":")[1]);
//				int sys = Integer.parseInt(cols[2].split(":")[1]);
//				double load = Double.parseDouble(cols[12].split(":")[1]);
//				
//				int free = Integer.parseInt(cols[4].split(":")[1]);
//				int buff = Integer.parseInt(cols[5].split(":")[1]);
//				int cache = Integer.parseInt(cols[6].split(":")[1]);
//				
//				int iobi = Integer.parseInt(cols[10].split(":")[1]);
//				int iobo = Integer.parseInt(cols[11].split(":")[1]);
//				
//				int swapbi = Integer.parseInt(cols[8].split(":")[1]);
//				int swapbo = Integer.parseInt(cols[9].split(":")[1]);
//				
//				int netrcv = Integer.parseInt(cols[13].split(":")[1]);
//				int netsnd = Integer.parseInt(cols[14].split(":")[1]);
				
				cpuUse.put(key, use);
				cpuSys.put(key, sys);
				cpuLoad.put(key, load);
				
				memFree.put(key, free);
				memBuff.put(key, buff);
				memCache.put(key, cache);
				
				ioBi.put(key, iobi);
				ioBo.put(key, iobo);
				
				swapBi.put(key, swapbi);
				swapBo.put(key, swapbo);
				
				netRcv.put(key, netrcv);
				netSnd.put(key, netsnd);
				
				i++;
			}
			
			in.close();
			isr.close();
			is.close();
			
			graph.graphCpu(cpuUse, cpuSys, cpuLoad, imgCpu, fileDir);
			graph.graphMem(memFree, memBuff, memCache, imgMem, fileDir);
			graph.graphIO(ioBi, ioBo, imgIO, fileDir);
			graph.graphSwap(swapBi, swapBo, imgSwap, fileDir);
			graph.graphNetIO(netRcv, netSnd, imgNet, fileDir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.doChart();
	}
	
	public static void main(String[] args) {
		String fileDir = "E:\\MyWork\\TestTeamSVN\\perfplantform\\jperfman";
		String fileName = "E:\\MyWork\\TestTeamSVN\\perfplantform\\jperfman\\analyse_all.log";
		int runtime = 1800;
		
		MonitorDataCharter demo = new MonitorDataCharter(fileName,fileDir,runtime);
		demo.init();
		demo.doChart();
	}
}

package com.jperfman.chart.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.jperfman.chart.util.MonDataGraph;



public class MonitorDataAvgCharter extends Thread {

	private List<String> fileName;
	private String fileDir;
	private int runTime;
	private int size;
	
	private MonDataGraph graph;
	
	private static final String imgCpu = "TOPCPU.png";
	private static final String imgMem = "TOPMEM.png";
	private static final String imgIO = "TOPIO.png";
	private static final String imgSwap = "TOPSWAP.png";
	private static final String imgNet = "TOPNET.png";
	
	public MonitorDataAvgCharter(List<String> fileName, String fileDir, int runTime) {
		this.fileName = fileName;
		this.fileDir = fileDir;
		this.runTime = runTime;
		this.size = fileName.size();
	}
	
	public void init() {
		graph = new MonDataGraph();
		graph.initPicSize(runTime);
	}
	
	public void doChart() {
		List<BufferedReader> inList = new ArrayList<BufferedReader>();
		List<FileInputStream> isList = new ArrayList<FileInputStream>();
		List<InputStreamReader> isrList = new ArrayList<InputStreamReader>();
		
		for (String filename : fileName) {
			File file = new File(filename);
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader in = new BufferedReader(isr);
				inList.add(in);
				isrList.add(isr);
				isList.add(is);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//如果采集监控信息发生异常，采集的文件都没有生成，那么不进行后续的画图逻辑
		if (inList.size() == 0 && isrList.size() == 0 && isList.size() == 0) {
			return;
		}
		
		List<Long> offsetList = new ArrayList<Long>();
		
		try {
			
			String line = null;
			int i = 1;
			long offset = 0;
			boolean breakFlag = false;
			
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
			
			while (true) {
				int useSum = 0, sysSum = 0;
				double loadSum = 0.0D;
				int freeSum = 0, buffSum = 0, cacheSum = 0;
				int iobiSum = 0, ioboSum = 0;
				int swapbiSum = 0, swapboSum = 0;
				int netrcvSum = 0, netsndSum = 0;
				int key = 0;
				
				for (int k=0;k<inList.size();k++) {
					line = inList.get(k).readLine();
					if (line == null) {
						breakFlag = true;
						break;
					}
					JSONObject obj = JSONObject.fromObject(line);
					Map<String,String> dataMap = (Map)obj;
					if (i == 1) {
						offset = Long.parseLong(dataMap.get("Time"));
						offsetList.add(k,offset);
					}
					//BugFix: 引入四舍五入是为了解决，监控数据第一个时间间隔总是小于预设的interval
					key = (int)Math.round(((Double.parseDouble(dataMap.get("Time")) - offsetList.get(k)) / 1000));
					
					useSum += Integer.parseInt(dataMap.get("CpuUse"));
					sysSum += Integer.parseInt(dataMap.get("CpuSys"));
					loadSum += Double.parseDouble(dataMap.get("LoadAvg1M"));
					
					freeSum += Integer.parseInt(dataMap.get("MemFree"));
					buffSum += Integer.parseInt(dataMap.get("MemBuff"));
					cacheSum += Integer.parseInt(dataMap.get("MemCach"));
					
					iobiSum += Integer.parseInt(dataMap.get("IOBi"));
					ioboSum += Integer.parseInt(dataMap.get("IOBo"));
					
					swapbiSum += Integer.parseInt(dataMap.get("SwapSi"));
					swapboSum += Integer.parseInt(dataMap.get("SwapSo"));
					
					netrcvSum += Integer.parseInt(dataMap.get("NetRcv"));
					netsndSum += Integer.parseInt(dataMap.get("NetSnd"));
				}
				
				if (breakFlag == true) {
					break;
				}
				
				cpuUse.put(key, useSum/size);
				cpuSys.put(key, sysSum/size);
				cpuLoad.put(key, loadSum/size);
				
				memFree.put(key, freeSum/size);
				memBuff.put(key, buffSum/size);
				memCache.put(key, cacheSum/size);
				
				ioBi.put(key, iobiSum/size);
				ioBo.put(key, ioboSum/size);
				
				swapBi.put(key, swapbiSum/size);
				swapBo.put(key, swapboSum/size);
				
				netRcv.put(key, netrcvSum/size);
				netSnd.put(key, netsndSum/size);
				
				i++;
			}
			
			for (BufferedReader in : inList) {
				in.close();
			}
			for (FileInputStream is : isList) {
				is.close();
			}
			for (InputStreamReader isr : isrList) {
				isr.close();
			}
			
			//DEBUG
//			for (int key : cpuUse.keySet()) {
//				System.out.println(key + "\t" + cpuUse.get(key));
//			}
			
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
	}
}

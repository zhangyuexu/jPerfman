package com.jperfman.chart.impl;

import java.util.ArrayList;
import java.util.List;

import com.jperfman.chart.plugin.AllRespTimeDotMaker;
import com.jperfman.chart.plugin.AllRespTimeLineMaker;
import com.jperfman.chart.plugin.AllThroughputMaker;
import com.jperfman.chart.plugin.IPlugin;
import com.jperfman.chart.plugin.IUserPlugin;
import com.jperfman.chart.plugin.UserRespTimeDotMaker;
import com.jperfman.chart.plugin.UserRespTimeLineMaker;
import com.jperfman.chart.plugin.UserThroughputMaker;
import com.jperfman.chart.util.PerfDataGraph;
import com.jperfman.chart.util.Tool;

public class PerfResultCharter extends Thread {

	private String filePath;
	private String fileName;
	private int runTime;
	private int interval;
	
	private List<String> userTimers;
	
	private PerfDataGraph graph;

	public PerfResultCharter(String fileName, String filePath, int runTime, int interval) {
		this.fileName = fileName;
		this.filePath = filePath;
		this.runTime = runTime;
		this.interval = interval;
	}
	
	public void init() {
		graph = new PerfDataGraph();
		graph.initPicSize(runTime);
		
		userTimers = Tool.getTimerList(fileName);
	}

	public void doChart() {
		List<Thread> listThd = new ArrayList<Thread>();
		
		List<IPlugin> listAll = new ArrayList<IPlugin>();
		listAll.add(new AllRespTimeDotMaker(fileName,filePath));
		listAll.add(new AllRespTimeLineMaker(fileName,filePath,interval));
		listAll.add(new AllThroughputMaker(fileName,filePath,interval));
		
		for (IPlugin plugin : listAll) {
			plugin.setGraph(graph);
			Thread worker = (Thread)plugin;
			worker.start();
			listThd.add(worker);
		}
		
		List<IUserPlugin> listPass = new ArrayList<IUserPlugin>();
		listPass.add(new UserRespTimeDotMaker(fileName,filePath));
		listPass.add(new UserRespTimeLineMaker(fileName,filePath,interval));
		listPass.add(new UserThroughputMaker(fileName,filePath,interval));

		for (String timer : userTimers) {
			for (IUserPlugin plugin : listPass) {
				plugin.setGraph(graph);
				plugin.setUserTimer(timer);
				Thread worker = (Thread) plugin;
				worker.start();
				listThd.add(worker);
			}
		}
		
		for (Thread thread : listThd) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		this.doChart();
	}
	
	public static void main(String[] args) {
		String fileName = "E:\\MyWork\\TestTeamSVN\\perfplantform\\jperfman\\result.csv";
		String filePath = "E:\\MyWork\\TestTeamSVN\\perfplantform\\jperfman\\pic";
		PerfResultCharter demo = new PerfResultCharter(args[0],args[1],Integer.parseInt(args[2]),5);
		long start = System.currentTimeMillis();
		demo.init();
		demo.run();
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}
}

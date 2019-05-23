package com.jperfman.chart.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import com.jperfman.chart.util.RunnerDataGraph;

public class ThreadViewCharter extends Thread {
	
	private String filePath;
	
	private String fileDir;
	
	private int runTime;
	
	private Map<Integer,Integer> threads;
	
	private RunnerDataGraph graph;
	
	private static final String imgName = "Threads_num.png";
	
	public ThreadViewCharter(String filePath, String fileDir, int runTime) {
		this.filePath = filePath;
		this.fileDir = fileDir;
		this.runTime = runTime;
		threads = new TreeMap<Integer,Integer>();
	}
	
	public void init() {
		graph = new RunnerDataGraph();
		graph.initPicSize(runTime);
	}
	
	public void doChart() {
		File file = new File(filePath);
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			
			String line;
			while ((line = in.readLine()) != null) {
				String[] cols = line.split(",");
				threads.put(Integer.parseInt(cols[0]),
						Integer.parseInt(cols[1]));
			}
			
			in.close();
			isr.close();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		graph.graphRunner(threads, imgName, fileDir);
	}
	
	public void run() {
		this.doChart();
	}
	
	public static void main(String[] args) {
		ThreadViewCharter demo = new ThreadViewCharter(
				"E:\\MyWork\\TestTeamSVN\\perfplantform\\jperfman\\thread_num_1514.csv",
				"E:\\MyWork\\TestTeamSVN\\perfplantform\\jperfman", 1800);
		demo.init();
		demo.doChart();
	}
}

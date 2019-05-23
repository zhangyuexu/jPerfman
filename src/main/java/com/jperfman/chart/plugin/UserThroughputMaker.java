package com.jperfman.chart.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import com.jperfman.chart.util.FileSaver;
import com.jperfman.chart.util.ParsedResult;
import com.jperfman.chart.util.Parser;
import com.jperfman.chart.util.PerfDataGraph;
import com.jperfman.util.Const;

public class UserThroughputMaker extends Thread implements IUserPlugin {

	private String resultCsv;
	private String fileDir;
	private TreeMap<Integer,Integer> dataAll;
	private TreeMap<Integer,Integer> dataPass;
	
	private int interval;
	
	private String userTimer;
	
	private static final String imgNamePass = "_TPS_Passed.png";
	private static final String imgNameAll = "_TPS_All.png";
	private static final String imgNameMulti = "_TPS_Contrast.png";
	
	private PerfDataGraph graph;
	
	public UserThroughputMaker(String filename, String fileDir, int interval) {
		this.resultCsv = filename;
		this.fileDir = fileDir;
		this.interval = interval;
		dataAll = new TreeMap<Integer,Integer>();
		dataPass = new TreeMap<Integer,Integer>();
	}
	
	public void setUserTimer(String userTimer) {
		this.userTimer = userTimer;
	}
	
	public void setGraph(PerfDataGraph graph) {
		this.graph = graph;
	}
	
	@Override
	public void analysis() {
		// TODO Auto-generated method stub
		File file = new File(resultCsv);
		double offset = 0;
		
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			
			String line = null;
			int i = 1;
			
			while ((line =in.readLine()) != null) {
				ParsedResult parsedResult = Parser.parse(line);
				
				if (i == 1) {
					offset = parsedResult.getElapsed();
				}
				
				int key = (int)(parsedResult.getElapsed() - offset) / interval +1;
				
				// an error or usertimer is empty, continue the loop
				if (!parsedResult.getError().equals("") || !parsedResult.getCustomTimer().containsKey(userTimer)) {
					if (dataAll.containsKey(key)) {
						int value = dataAll.get(key);
						dataAll.put(key, value+1);
					} else {
						dataAll.put(key, 1);
					}
					
					if (!dataPass.containsKey(key)) {
						dataPass.put(key, 0);
					}
				} else {
					if (dataPass.containsKey(key)) {
						int value = dataPass.get(key);
						dataPass.put(key, value+1);
					} else {
						dataPass.put(key, 1);
					}
					
					if (dataAll.containsKey(key)) {
						int value = dataAll.get(key);
						dataAll.put(key, value+1);
					} else {
						dataAll.put(key, 1);
					}
				}
				i++;
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
		
		Map<Integer,Integer> tpsPass = new TreeMap<Integer,Integer>();
		Map<Integer,Integer> tpsAll = new TreeMap<Integer,Integer>();
		
		for (int key : dataPass.keySet()) {
			int vPass = dataPass.get(key);
			int vAll = dataAll.get(key);
			tpsPass.put(key*interval, vPass/interval);
			tpsAll.put(key*interval,vAll/interval);
		}
		
		dataPass = null;
		dataAll = null;
		
		if (Const.SAVE_TMPRESULT == 1) {
			FileSaver.saveData(tpsPass, fileDir + File.separator + userTimer + "_All_Transactions_TPS_Passed.txt");
			FileSaver.saveData(tpsAll, fileDir + File.separator + userTimer + "_All_Transactions_TPS_All.txt");
		}
		
		graph.tpsGraph(tpsPass, userTimer+imgNamePass, fileDir);
		graph.tpsGraph(tpsAll, userTimer+imgNameAll, fileDir);
		graph.tpsGraphMulti(tpsPass, tpsAll, userTimer+imgNameMulti, fileDir);
	}
	
	public void run() {
		analysis();
	}
}

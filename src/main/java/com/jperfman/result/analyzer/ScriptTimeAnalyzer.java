package com.jperfman.result.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.jperfman.chart.util.ParsedResult;
import com.jperfman.chart.util.Parser;
import com.jperfman.chart.util.Tool;

public class ScriptTimeAnalyzer extends BaseAnalyzer implements Callable<Map<String,Double>> {
	
	private Map<String,Double> scriptTimeSummary;
	
	public ScriptTimeAnalyzer(String filename, int runTime, int interval) {
		super(filename,runTime,interval);
		
		this.scriptTimeSummary = new HashMap<String,Double>();
	}
	
	private List<Double> load() {
		List<Double> scriptTimeList = new ArrayList<Double>();
		File file = new File(super.filename);
		
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			
			String line;
			ParsedResult result = null;
			
			while ((line = in.readLine()) != null) {
				result = Parser.parse(line);
				
				
				if (result.getElapsed() < super.runTime) {
					scriptTimeList.add(result.getScriptRunTime());
				}
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
		
		return scriptTimeList;
	}
	
	private void statistic(List<Double> list) {
		Tool.sortList(list);
		double avg = Tool.getAverage(list);
		
		this.scriptTimeSummary.put("pass_num", (double)list.size());
		this.scriptTimeSummary.put("min_resp", list.get(0));
//		this.scriptTimeSummary.put("min_resp", Tool.getMin(list));
		this.scriptTimeSummary.put("max_resp", list.get(list.size()-1));
//		this.scriptTimeSummary.put("max_resp", Tool.getMax(list));
		this.scriptTimeSummary.put("avg_resp", avg);
		this.scriptTimeSummary.put("perc_80", list.get((int)(list.size() * (80/100.0D))));
		this.scriptTimeSummary.put("perc_90", list.get((int)(list.size() * (90/100.0D))));
		this.scriptTimeSummary.put("perc_95", list.get((int)(list.size() * (95/100.0D))));
		this.scriptTimeSummary.put("standard", Tool.getStdev(list, avg));
	}
	
	public Map<String,Double> call() {
		List<Double> scriptTimeList = load();
		statistic(scriptTimeList);
		return scriptTimeSummary;
	}
	
	public static void main(String[] args) {
		ScriptTimeAnalyzer demo = new ScriptTimeAnalyzer(args[0], Integer.parseInt(args[1]), 5);
		long start = System.currentTimeMillis();
		demo.call();
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}
}

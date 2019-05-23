package com.jperfman.result.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.jperfman.chart.util.ParsedResult;
import com.jperfman.chart.util.Parser;

public class ScriptTimeAnalyzerEx extends BaseAnalyzer implements Callable<Map<String,Double>> {
	
	private Map<String,Double> scriptTimeSummary;
	
	private double sum = 0;
	private double min = Double.MAX_VALUE;
	private double max = Double.MIN_VALUE;
	
	public ScriptTimeAnalyzerEx(String filename, int runTime, int interval) {
		super(filename,runTime,interval);
		
		this.scriptTimeSummary = new HashMap<String,Double>();
	}
	
	private void load() {
		File file = new File(super.filename);
		
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			
			String line;
			ParsedResult result = null;
			
			while ((line = in.readLine()) != null) {
				result = Parser.parse(line);
				super.totalCnt++;
				
				if (result.getElapsed() < super.runTime) {
					double data = result.getScriptRunTime();
					
					if (data < min) {
						min = data;
					}
					if (data > max) {
						max = data;
					}
					sum += data;
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
	}
	
	private void statistic() {
		this.scriptTimeSummary.put("pass_num", super.totalCnt);
		this.scriptTimeSummary.put("min_resp", min);
//		this.scriptTimeSummary.put("min_resp", Tool.getMin(list));
		this.scriptTimeSummary.put("max_resp", max);
//		this.scriptTimeSummary.put("max_resp", Tool.getMax(list));
		this.scriptTimeSummary.put("avg_resp", Double.parseDouble(String.format("%.3f", sum / super.totalCnt)));
		this.scriptTimeSummary.put("perc_80", 0.0D);
		this.scriptTimeSummary.put("perc_90", 0.0D);
		this.scriptTimeSummary.put("perc_95", 0.0D);
		this.scriptTimeSummary.put("standard", 0.0D);
	}
	
	public Map<String,Double> call() {
		load();
		statistic();
		return scriptTimeSummary;
	}
	
	public static void main(String[] args) {
		ScriptTimeAnalyzerEx demo = new ScriptTimeAnalyzerEx(args[0], Integer.parseInt(args[1]), 5);
		long start = System.currentTimeMillis();
		demo.call();
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}
}

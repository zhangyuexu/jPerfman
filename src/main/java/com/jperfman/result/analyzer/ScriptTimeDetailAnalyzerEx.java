package com.jperfman.result.analyzer;

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
import java.util.concurrent.Callable;

import com.jperfman.chart.util.ParsedResult;
import com.jperfman.chart.util.Parser;
import com.jperfman.chart.util.Tool;

public class ScriptTimeDetailAnalyzerEx extends BaseAnalyzer implements Callable<Map<Integer,List<Double>>> {
	
	private Map<Integer,List<Double>> scriptTimeDetail;

	public ScriptTimeDetailAnalyzerEx(String filename, int runTime, int interval) {
		super(filename, runTime, interval);
		// TODO Auto-generated constructor stub
		this.scriptTimeDetail = new TreeMap<Integer,List<Double>>();
	}
	
	private Map<Integer,List<Double>> load() {
		Map<Integer,List<Double>> scriptTimeIntervalMap = new TreeMap<Integer,List<Double>>();
		File file = new File(super.filename);
		
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			
			String line;
			ParsedResult result = null;
			
			int i = 1;
			double offset = 0;
			
			while ((line = in.readLine()) != null) {
				result = Parser.parse(line);
				
				if (i++ == 1) {
					offset = result.getElapsed();
				}
				
				if (result.getElapsed() < super.runTime) {
					int key = (int) (result.getElapsed() - offset) / interval;
					if (scriptTimeIntervalMap.containsKey(key)) {
						List<Double> list = scriptTimeIntervalMap.get(key);
						double data = result.getScriptRunTime();
						list.set(0,list.get(0) + data);			// index 0 is sum
						if (list.get(1) > data) {
							list.set(1, data);					// index 1 is min
						}
						if (list.get(2) < data) {
							list.set(2, data);					// index 2 is max
						}
						list.set(3, list.get(3)+1);				// index 3 is count
						scriptTimeIntervalMap.put(key, list);
					} else {
						List<Double> list = new ArrayList<Double>();
						list.add(result.getScriptRunTime());
						list.add(Double.MAX_VALUE);
						list.add(Double.MIN_VALUE);
						list.add(1D);
						scriptTimeIntervalMap.put(key, list);
					}
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
		
		return scriptTimeIntervalMap;
	}
	
	private void allPointStatistic(Map<Integer,List<Double>> map) {
		double rate, min, max, avg, pct80, pct90, pct95, stdev;
		Double cnt;
		int intervalStart;
		
		for (int key : map.keySet()) {
			intervalStart = (key + 1) * interval;
			cnt = map.get(key).get(3);
			
			if (cnt == 0) {
				rate = 0.0D;
				min = 0.0D;
				max = 0.0D;
				avg = 0.0D;
				pct80 = 0.0D;
				pct90 = 0.0D;
				pct95 = 0.0D;
				stdev = 0.0D;
			} else {
				List<Double> list = map.get(key);
				rate = cnt / interval;
				min = list.get(1);
				max = list.get(2);
				avg = Double.parseDouble(String.format("%.3f", list.get(0) / cnt));
				pct80 = 0D;
				pct90 = 0D;
				pct95 = 0D;
				stdev = 0D;
			}
			List<Double> tmp = new ArrayList<Double>();
			tmp.add(cnt);
			tmp.add(rate);
			tmp.add(min);
			tmp.add(avg);
			tmp.add(pct80);
			tmp.add(pct90);
			tmp.add(pct95);
			tmp.add(max);
			tmp.add(stdev);
			this.scriptTimeDetail.put(intervalStart, tmp);
		}
	}

	public Map<Integer,List<Double>> call() {
		// TODO Auto-generated method stub
		Map<Integer,List<Double>> map = load();
		allPointStatistic(map);
		return scriptTimeDetail;
	}
	
	public static void main(String[] args) {
		ScriptTimeDetailAnalyzerEx demo = new ScriptTimeDetailAnalyzerEx(args[0], Integer.parseInt(args[1]), 5);
		long start = System.currentTimeMillis();
		demo.call();
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}

}

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

public class UserTimeDetailAnalyzer extends BaseAnalyzer implements Callable<Map<Integer,List<Double>>> {
	
	private String userTimer;
	
	private Map<Integer,List<Double>> userTimeDetail;

	public UserTimeDetailAnalyzer(String filename, int runTime, int interval, String userTimer) {
		super(filename, runTime, interval);
		// TODO Auto-generated constructor stub
		this.userTimer = userTimer;
		this.userTimeDetail = new TreeMap<Integer,List<Double>>();
	}
	
	private Map<Integer,List<Double>> load() {
		Map<Integer,List<Double>> userTimeIntervalMap = new TreeMap<Integer,List<Double>>();
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
				
				if (result.getError().equals("")
						&& result.getCustomTimer().size() != 0) {
					// 计算页面上用户统计的事务响应时间的基础数据
					int key = (int) (result.getElapsed() - offset) / interval;
					if (userTimeIntervalMap.containsKey(key)) {
						List<Double> list = userTimeIntervalMap.get(key);
						list.add(result.getCustomTimer().get(userTimer));
						userTimeIntervalMap.put(key, list);
					} else {
						List<Double> list = new ArrayList<Double>();
						list.add(result.getCustomTimer().get(userTimer));
						userTimeIntervalMap.put(key, list);
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
		
		return userTimeIntervalMap;
	}
	
	private void userPointStatistic(Map<Integer,List<Double>> map) {
		double rate, min, max, avg, pct80, pct90, pct95, stdev;
		int cnt;
		int intervalStart;
		
		for (int key : map.keySet()) {
			intervalStart = (key + 1) * interval;
			cnt = map.get(key).size();
			
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
				rate = (double)cnt / interval;
				List<Double> list = map.get(key);
				Tool.sortList(list);
				min = list.get(0);
				max = list.get(list.size()-1);
				avg = Tool.getAverage(list);
				pct80 = list.get((int)(list.size() * (80/100.0D)));
				pct90 = list.get((int)(list.size() * (90/100.0D)));
				pct95 = list.get((int)(list.size() * (95/100.0D)));
				stdev = Tool.getStdev(list,avg);
			}
			List<Double> tmp = new ArrayList<Double>();
			tmp.add((double)cnt);
			tmp.add(rate);
			tmp.add(min);
			tmp.add(avg);
			tmp.add(pct80);
			tmp.add(pct90);
			tmp.add(pct95);
			tmp.add(max);
			tmp.add(stdev);
			this.userTimeDetail.put(intervalStart, tmp);
		}
	}

	@Override
	public Map<Integer,List<Double>> call() {
		// TODO Auto-generated method stub
		Map<Integer,List<Double>> map = load();
		userPointStatistic(map);
		return userTimeDetail;
	}
	
	public static void main(String[] args) {
		UserTimeDetailAnalyzer demo = new UserTimeDetailAnalyzer(args[0], Integer.parseInt(args[1]), 5, "user_timer");
		long start = System.currentTimeMillis();
		demo.call();
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}
	
}

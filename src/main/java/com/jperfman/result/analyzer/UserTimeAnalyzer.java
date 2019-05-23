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

public class UserTimeAnalyzer extends BaseAnalyzer implements Callable<Map<String,Double>> {
	
	private String userTimer;
	
	private Map<String,Double> userTimeSummary;

	public UserTimeAnalyzer(String filename, int runTime, int interval, String userTimer) {
		super(filename, runTime, interval);
		// TODO Auto-generated constructor stub
		this.userTimer = userTimer;
		userTimeSummary = new HashMap<String,Double>();
	}
	
	private List<Double> load() {
		List<Double> userTimeList = new ArrayList<Double>();
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
					if (result.getError().equals("")
							&& result.getCustomTimer().size() != 0) {
						// 计算页面上用户统计的事务响应时间的基础数据
						userTimeList
								.add(result.getCustomTimer().get(userTimer));
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
		
		return userTimeList;
	}
	
	private void userTimeStatistic(List<Double> list) {
		Tool.sortList(list);
		double avg = Tool.getAverage(list);
		
		this.userTimeSummary.put("pass_num", (double)list.size());
		this.userTimeSummary.put("min_resp", list.get(0));
		this.userTimeSummary.put("max_resp", list.get(list.size()-1));
		this.userTimeSummary.put("avg_resp", avg);
		this.userTimeSummary.put("perc_80", list.get((int)(list.size() * (80/100.0D))));
		this.userTimeSummary.put("perc_90", list.get((int)(list.size() * (90/100.0D))));
		this.userTimeSummary.put("perc_95", list.get((int)(list.size() * (95/100.0D))));
		this.userTimeSummary.put("standard", Tool.getStdev(list, avg));
	}

	public Map<String,Double> call() {
		// TODO Auto-generated method stub
		List<Double> list = load();
		userTimeStatistic(list);
		return userTimeSummary;
	}
	
	public static void main(String[] args) {
		UserTimeAnalyzer demo = new UserTimeAnalyzer(args[0], Integer.parseInt(args[1]), 5, "user_timer");
		long start = System.currentTimeMillis();
		demo.call();
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}

}

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
import java.util.TreeMap;
import java.util.concurrent.Callable;

import com.jperfman.chart.util.ParsedResult;
import com.jperfman.chart.util.Parser;
import com.jperfman.util.Const;

public class ThreadTpsAnalyzer extends BaseAnalyzer implements Callable<Map<Integer,List<Integer>>> {
	
	private String userTimer;
	private String fileDir;
	private Map<Integer,List<Integer>> threadTpsResp;
	
	private static final String TPSALL = "tps_all";
	private static final String TPSPASS = "tps_pass";
	
	public ThreadTpsAnalyzer(String filename, int runTime, int interval, String fileDir, String userTimer) {
		super(filename, runTime, interval);
		// TODO Auto-generated constructor stub
		this.userTimer = userTimer;
		this.fileDir = fileDir;
		this.threadTpsResp = new TreeMap<Integer,List<Integer>>();
	}
	
	private Map<Integer,Map<String,Double>> load() {
		Map<Integer,Map<String,Double>> threadTpsRespIntervalMap = new TreeMap<Integer,Map<String,Double>>();
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
					//计算线程维度TPS,响应时间的基础数据
					if (threadTpsRespIntervalMap.containsKey(key)) {
						if (!result.getError().equals("") || result.getCustomTimer().size() == 0) {
							Map<String,Double> tmpMap = threadTpsRespIntervalMap.get(key);
							Double val = tmpMap.get(TPSALL);
							tmpMap.put(TPSALL, val + 1);
//							threadTpsRespIntervalMap.put(key, tmpMap);
						} else {
							Map<String,Double> tmpMap = threadTpsRespIntervalMap.get(key);
							Double val1 = tmpMap.get(TPSALL);
							Double val2 = tmpMap.get(TPSPASS);
							Double val3 = tmpMap.get(userTimer);
							tmpMap.put(TPSALL, val1 + 1);
							tmpMap.put(TPSPASS, val2 + 1);
							tmpMap.put(userTimer, val3 + result.getCustomTimer().get(userTimer));
							tmpMap.put("len", tmpMap.get("len")+1);
//							threadTpsRespIntervalMap.put(key, tmpMap);
						}
					} else {
						if (!result.getError().equals("") || result.getCustomTimer().size() == 0) {
							Map<String,Double> tmpMap = new HashMap<String,Double>();
							tmpMap.put(TPSALL, (double) 1);
							tmpMap.put(TPSPASS, (double) 0);
							tmpMap.put(userTimer, (double)0);
							tmpMap.put("len", (double) 0);
							threadTpsRespIntervalMap.put(key, tmpMap);
						} else {
							Map<String,Double> tmpMap = new HashMap<String,Double>();
							tmpMap.put(TPSALL, (double) 1);
							tmpMap.put(TPSPASS, (double) 1);
							tmpMap.put(userTimer, result.getCustomTimer().get(userTimer));
							tmpMap.put("len", (double)1);
							threadTpsRespIntervalMap.put(key, tmpMap);
						}
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
		
		return threadTpsRespIntervalMap;
	}
	
	private void threadTpsRespStatistic(Map<Integer,Map<String,Double>> map) {
		Map<Integer,Integer> threadNumMap = new TreeMap<Integer,Integer>();
		
		File file = new File(fileDir + File.separator + Const.THREAD_NUM_FILE);
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader in = new BufferedReader(isr);
			
			Map<Integer,List<Map<String,Double>>> tmpMap = new HashMap<Integer,List<Map<String,Double>>>();
			
			String line = null;
			while ((line = in.readLine()) != null) {
				String[] cols = line.split(",");
				if (cols.length == 2) {
					threadNumMap.put(Integer.parseInt(cols[0]), Integer.parseInt(cols[1]));
				}
			}
			
			in.close();
			isr.close();
			fis.close();
			
//			System.out.println(threadNumMap);
			
			for (int key : map.keySet()) {
				int intervalTh = key * interval;
				int threadKey = 0;
				
//				System.out.print(intervalTh + ",");
				
				if (threadNumMap.containsKey(intervalTh)) {
					threadKey = threadNumMap.get(intervalTh);
				} else {
					threadKey = 0;
				}
				
				if (threadKey != 0) {
					if (tmpMap.containsKey(threadKey)) {
						List<Map<String, Double>> list = tmpMap.get(threadKey);
						list.add(map.get(key));
					} else {
						List<Map<String, Double>> list = new ArrayList<Map<String, Double>>();
						list.add(map.get(key));
						tmpMap.put(threadKey, list);
					}
				}
			}
			
			for (int key : tmpMap.keySet()) {
				Double sumTpsPass = 0.0D;
				Double sumTpsAll = 0.0D;
				Double sumResp = 0.0D;
				int subLen = 0;
				int len = tmpMap.get(key).size();
				
				for (Map<String,Double> item : tmpMap.get(key)) {
					sumTpsPass += item.get(TPSPASS);
					sumTpsAll += item.get(TPSALL);
					sumResp += item.get(userTimer);
					subLen += item.get("len");
				}
				List<Integer> list = new ArrayList<Integer>();
				list.add((int)(sumTpsPass/(len * interval)));
				list.add((int)(sumTpsAll/(len * interval)));
				list.add((int)(sumResp/(subLen)*1000));
				threadTpsResp.put(key, list);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map<Integer, List<Integer>> call() throws Exception {
		// TODO Auto-generated method stub
		Map<Integer,Map<String,Double>> map = load();
		threadTpsRespStatistic(map);
		return threadTpsResp;
	}

	public static void main(String[] args) {
		ThreadTpsAnalyzer demo = new ThreadTpsAnalyzer(
				args[0],
				Integer.parseInt(args[2]), 5,
				args[1],
				"user_timer");
		long start = System.currentTimeMillis();
		try {
			System.out.println(demo.call());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}
}

package com.jperfman.chart.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.jperfman.chart.util.ParsedResult;
import com.jperfman.chart.util.Parser;
import com.jperfman.chart.util.PerfDataGraph;
import com.jperfman.chart.util.Tool;
import com.jperfman.util.Const;

public class AllRespTimeLineMaker extends Thread implements IPlugin {

	private String resultCsv;
	private String fileDir;
	private TreeMap<Integer,Double> dataAvg;
	private TreeMap<Integer,Double> data80Pct;
	private TreeMap<Integer,Double> data90Pct;
	
	private TreeMap<Integer,List<Double>> tmpData;
	
	private int interval;
	
	private PerfDataGraph graph;
	
	private static final int distance = 30;
	
	private static final String imgName = "All_Transactions_responsetime_linechart.png";
	
	public AllRespTimeLineMaker(String filename, String fileDir, int interval) {
		this.resultCsv = filename;
		this.fileDir = fileDir;
		this.interval = interval;
		tmpData = new TreeMap<Integer,List<Double>>();
		dataAvg = new TreeMap<Integer,Double>();
		data80Pct = new TreeMap<Integer,Double>();
		data90Pct = new TreeMap<Integer,Double>();
	}
	
	public void setGraph(PerfDataGraph graph) {
		this.graph = graph;
	}
	
	@Override
	public void analysis() {
		// TODO Auto-generated method stub
		File file = new File(resultCsv);
		
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			
			double offset = 0;
			int i = 1;
			int key = 0;
			String line;
			
			while ((line = in.readLine()) != null) {
				ParsedResult parsedResult = Parser.parse(line);
				if (i == 1) {
					offset = parsedResult.getElapsed();
				}
				
				if (!parsedResult.getError().equals("") || parsedResult.getCustomTimer().size() == 0) {
					continue;
				} else {
					key = (int)(parsedResult.getElapsed() - offset) /interval + 1;
					
					if (tmpData.containsKey(key)) {
						tmpData.get(key).add(parsedResult.getScriptRunTime());
					} else {
						if (tmpData.containsKey(key - distance)) {
							List<Double> list = tmpData.get(key - distance);
							if (list != null && list.size() > 0) {
								double avg = Tool.getAverage(list);
								Tool.sortList(list);
								double pct80 = Tool.getPct(list, 80);
								double pct90 = Tool.getPct(list, 90);
								
								if (Const.SAVE_TMPRESULT == 1) {
									
								}
								
								dataAvg.put((key - distance) * interval, avg);
								data80Pct.put((key - distance) * interval, pct80);
								data90Pct.put((key - distance) * interval, pct90);
							} else {
								if (Const.SAVE_TMPRESULT == 1) {
									
								}
								
								dataAvg.put((key - distance) * interval, 0.0D);
								data80Pct.put((key - distance) * interval, 0.0D);
								data90Pct.put((key - distance) * interval, 0.0D);
							}
							
							tmpData.remove(key - distance);
						}
						
						List<Double> tmp = new ArrayList<Double>();
						tmp.add(parsedResult.getScriptRunTime());
						tmpData.put(key, tmp);
					}
					i++;
				}
			}
			
			in.close();
			isr.close();
			is.close();
			
			//tmpData is not empty, we should handle the left data
			if (tmpData.size() > 0) {
				for (int tmpKey : tmpData.keySet()) {
					List<Double> list = tmpData.get(tmpKey);
					
					if (list != null && list.size() > 0) {
						double avg = Tool.getAverage(list);
						Tool.sortList(list);
						double pct80 = Tool.getPct(list, 80);
						double pct90 = Tool.getPct(list, 90);
						
						if (Const.SAVE_TMPRESULT == 1) {
							
						}
						
						dataAvg.put(tmpKey * interval, avg);
						data80Pct.put(tmpKey * interval, pct80);
						data90Pct.put(tmpKey * interval, pct90);
					} else {
						if (Const.SAVE_TMPRESULT == 1) {
							
						}
						
						dataAvg.put(tmpKey * interval, 0.0D);
						data80Pct.put(tmpKey * interval, 0.0D);
						data90Pct.put(tmpKey * interval, 0.0D);
					}
					
					list = null;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tmpData = null;
		graph.respGraphLine(dataAvg, data80Pct, data90Pct, imgName, fileDir);
	}

	public void run() {
		analysis();
	}
	
	public static void main(String[] args) {
		AllRespTimeLineMaker demo = new AllRespTimeLineMaker(args[0],args[1],5);
		PerfDataGraph graph = new PerfDataGraph();
		graph.initPicSize(Integer.parseInt(args[2]));
		demo.setGraph(graph);
		long start = System.currentTimeMillis();
		demo.run();
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}
}

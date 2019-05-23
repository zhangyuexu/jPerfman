package com.jperfman.chart.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

import com.jperfman.chart.util.FileSaver;
import com.jperfman.chart.util.Graph;
import com.jperfman.chart.util.ParsedResult;
import com.jperfman.chart.util.Parser;
import com.jperfman.chart.util.PerfDataGraph;
import com.jperfman.util.Const;

public class AllRespTimeDotMaker extends Thread implements IPlugin {

	private String resultCsv;
	private String fileDir;
	private TreeMap<Double,Double> data;
	
	private static final String imgName = "All_Transactions_responsetime_dotchart.png";
	
	private PerfDataGraph graph;
	
	public AllRespTimeDotMaker(String filename, String fileDir) {
		this.resultCsv = filename;
		this.fileDir = fileDir;
		data = new TreeMap<Double,Double>();
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
			
			String line = null;
			while ((line =in.readLine()) != null) {
				ParsedResult parsedResult = Parser.parse(line);
				
				// an error or usertimer is empty, continue the loop
				if (!parsedResult.getError().equals("") || parsedResult.getCustomTimer().size() == 0) {
					continue;
				} else {
					double elapsed = parsedResult.getElapsed();
					double scriptRuntime = parsedResult.getScriptRunTime();
					data.put(elapsed, scriptRuntime);
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
		
		if (Const.SAVE_TMPRESULT == 1) {
			FileSaver.saveData(data, fileDir + File.separator + "All_responsetime_dotchart.txt");
		}
		graph.respGraphRaw(data, imgName, fileDir);
	}
	
	public void run() {
		analysis();
	}
	
	public static void main(String[] args) {
		
		AllRespTimeDotMaker demo = new AllRespTimeDotMaker(args[0],args[1]);
		PerfDataGraph graph = new PerfDataGraph();
		graph.initPicSize(Integer.parseInt(args[2]));
		demo.setGraph(graph);
		long start = System.currentTimeMillis();
		demo.run();
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}

}

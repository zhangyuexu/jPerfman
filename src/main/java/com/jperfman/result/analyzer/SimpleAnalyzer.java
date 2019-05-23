package com.jperfman.result.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jperfman.chart.util.ParsedResult;
import com.jperfman.chart.util.Parser;

public class SimpleAnalyzer extends Thread {
	private String filename;
	
	private String startTime;
	private String endTime;
	
	private int totalTransactions;
	private int totalErrors;
	
	public SimpleAnalyzer(String filename) {
		this.filename = filename;
	}
	
	private void analyze() {
		File file = new File(filename);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			
			String line;
			ParsedResult result = null;
			
			while ((line = in.readLine()) != null) {
				result = Parser.parse(line);
				
				if (!result.getError().equals("")) {
					totalErrors += 1;
				}
				if (totalTransactions == 0) {
					startTime = sdf.format(new Date(result.getEpoch()));
				}
				totalTransactions += 1;
				
			}
			
			endTime = sdf.format(new Date(result.getEpoch()));
			
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
	
	public int getTotalTransactions() {
		return this.totalTransactions;
	}
	
	public int getTotalErrors() {
		return this.totalErrors;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void run() {
		analyze();
	}
	
	public static void main(String[] args) {
		SimpleAnalyzer demo = new SimpleAnalyzer(args[0]);
		long start = System.currentTimeMillis();
		demo.run();
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end-start)/1000);
	}
}

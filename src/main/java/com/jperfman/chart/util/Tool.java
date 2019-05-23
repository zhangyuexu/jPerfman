package com.jperfman.chart.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tool {
	public static void sortList(List<Double> list) {
		Collections.sort(list);
	}
	
	public static double getAverage(List<Double> list) {
		double avg = 0;
		double sum = 0;
		
		for (double item : list) {
			sum = sum + item;
		}
		avg = sum / list.size();
		return Double.parseDouble(String.format("%.3f", avg));
	}
	
	public static double getPct(List<Double> list, int pct) {
		int index = (int)(list.size() * (pct / 100.0D));
//		Collections.sort(list);
		return (double)list.get(index);
	}
	
	public static double getStdev(List<Double> list, double avg) {
//		double avg = getAverage(list);
		double tmp = 0;
		double stdev = 0;
		
		for (Double item : list) {
			tmp += Math.pow((item - avg),2);
		}
		stdev = Math.sqrt(tmp / (list.size() - 1));
		
		return Double.parseDouble(String.format("%.3f", stdev));
	}
	
	public static double getMin(List<Double> list) {
		double min = Double.MAX_VALUE;
		for (Double item : list) {
			if (item < min) {
				min = item;
			}
		}
		return min;
	}
	
	public static double getMax(List<Double> list) {
		double max = Double.MIN_VALUE;
		for (Double item : list) {
			if (item > max) {
				max = item;
			}
		}
		return max;
	}
	
	public static List<String> getTimerList(String filename) {
		File file = new File(filename);
		List<String> result = new ArrayList<String>();
		
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
	        BufferedReader in = new BufferedReader(isr);
	        String line;
	        
	        while ((line = in.readLine()) != null) {
	        	ParsedResult parsedResult = Parser.parse(line);
	        	if (parsedResult.getCustomTimer().size() > 0) {
	        		for (String key : parsedResult.getCustomTimer().keySet()) {
	        			result.add(key);
	        		}
	        		break;
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
        
        return result;
	}
}

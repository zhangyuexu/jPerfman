package com.jperfman.chart.util;

import java.util.HashMap;
import java.util.Map;

public class Parser {
	
	public static ParsedResult parse(String line) {
		String[] items = line.split(",");
//		String id = items[0];
		String elapsed = items[1];
		String epoch = items[2];
		String name = items[3];
		String scriptRuntime = items[4];
		String error = items[5];
		String customTimer = items[6];
		
		Map<String,Double> customTimers = new HashMap<String,Double>();
		if (!customTimer.equals("{}")) {
			String customTimerContent = customTimer.substring(1,
					customTimer.length() - 1);

			String[] mapItems = customTimerContent.split(",");

			for (int i = 0; i < mapItems.length; i++) {
				String[] cols = mapItems[i].split("=");
//				String[] cols = mapItems[i].split(":");
				customTimers.put(cols[0], Double.parseDouble(cols[1].trim()));
			}
		}
		
		return new ParsedResult(name, Double.parseDouble(elapsed),
				Long.parseLong(epoch), Double.parseDouble(scriptRuntime),
				customTimers, error);
	}
}
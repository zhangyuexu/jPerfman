package com.jperfman.chart.util;

import java.util.ArrayList;
import java.util.List;

public class UserTimerSplitter {

	public static List<String> split(ParsedResult param) {
		List<String> result = new ArrayList<String>();
		
		for (Object key : param.getCustomTimer().keySet()) {
			result.add((String) key);
		}
		return result;
	}
}

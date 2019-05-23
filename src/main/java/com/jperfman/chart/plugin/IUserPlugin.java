package com.jperfman.chart.plugin;

import com.jperfman.chart.util.PerfDataGraph;

public interface IUserPlugin {
	public void analysis();
	
	public void setGraph(PerfDataGraph graph);
	
	public void setUserTimer(String userTimer);
}

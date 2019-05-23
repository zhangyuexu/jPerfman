package com.jperfman.chart.plugin;

import com.jperfman.chart.util.PerfDataGraph;

public interface IPlugin {
	public void analysis();
	
	public void setGraph(PerfDataGraph graph);
}

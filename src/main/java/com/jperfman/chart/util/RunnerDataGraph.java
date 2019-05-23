package com.jperfman.chart.util;

import java.awt.Color;
import java.io.File;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class RunnerDataGraph extends Graph {
	public void graphRunner(Map<Integer, Integer> runners, String imgName,
			String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries seriesRunner = new XYSeries("Threads");

		for (Integer key : runners.keySet()) {
			seriesRunner.add(key, runners.get(key));
		}

		runners.clear();

		dataset.addSeries(seriesRunner);
		String xLabel = "Elapsed Time In Test (secs)";
		String yLabel = "Threads Number";

		jFreeChart = ChartFactory.createXYLineChart("Threads Number Infomation",
				xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, false,
				false);
		super.paramSetting(jFreeChart, Color.green, null, null);
		super.outputPNG(imgDir + File.separator + imgName, jFreeChart);
	}
}

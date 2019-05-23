package com.jperfman.chart.util;

import java.awt.Color;
import java.io.File;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PerfDataGraph extends Graph {

	public void respGraphRaw(Map<Double,Double> data, String imgName, String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("",true);
		
		for (Double key : data.keySet()) {
			series.add(key, data.get(key));
		}
		
		data.clear();
		System.gc();
		
		dataset.addSeries(series);
		
		String xLabel = "Elapsed Time In Test (secs)";
		String yLabel = "Response Time (secs)";
		jFreeChart = ChartFactory.createScatterPlot(
				imgName.replaceAll(".png", ""), xLabel, yLabel, dataset,
				PlotOrientation.VERTICAL, true, false, false);
		paramSetting(jFreeChart, Color.blue, null, null);
		outputPNG(imgDir + File.separator + imgName, jFreeChart);
	}
	
	public void tpsGraph(Map<Integer,Integer> data, String imgName, String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("",true);
		
		for (int key : data.keySet()) {
			series.add(key, data.get(key));
		}
		
		dataset.addSeries(series);
		String xLabel = "Elapsed Time In Test (secs)";
		String yLabel = "Transactions Per Second (count)";
		jFreeChart = ChartFactory.createXYLineChart(
				imgName.replaceAll(".png", ""), xLabel, yLabel, dataset,
				PlotOrientation.VERTICAL, true, false, false);
		
		paramSetting(jFreeChart, Color.blue, null, null);
		outputPNG(imgDir + File.separator + imgName, jFreeChart);
	}
	
	public void tpsGraphMulti(Map<Integer, Integer> tpsPass,
			Map<Integer, Integer> tpsAll, String imgName, String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries seriesPass = new XYSeries("TPS_Passed");
		XYSeries seriesAll = new XYSeries("TPS_All");

		for (int key : tpsPass.keySet()) {
			seriesPass.add(key, tpsPass.get(key));
			seriesAll.add(key, tpsAll.get(key));
		}

		tpsPass.clear();
		tpsAll.clear();

		dataset.addSeries(seriesPass);
		dataset.addSeries(seriesAll);
		String xLabel = "Elapsed Time In Test (secs)";
		String yLabel = "Transactions Per Second (count)";

		jFreeChart = ChartFactory.createXYLineChart(
				imgName.replaceAll(".png", ""), xLabel, yLabel, dataset,
				PlotOrientation.VERTICAL, true, false, false);

		paramSetting(jFreeChart, Color.blue, Color.red, null);
		outputPNG(imgDir + File.separator + imgName,jFreeChart);
	}
	
	public void respGraphLine(Map<Integer, Double> avg,
			Map<Integer, Double> pct80, Map<Integer, Double> pct90,
			String imgName, String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
	    XYSeries seriesAvg = new XYSeries("Avg");
	    XYSeries seriesP80 = new XYSeries("80pct");
	    XYSeries seriesP90 = new XYSeries("90pct");
	    
	    for (int key : avg.keySet()) {
	    	seriesAvg.add(key, avg.get(key));
	    	seriesP80.add(key,pct80.get(key));
	    	seriesP90.add(key, pct90.get(key));
	    }
	    
	    avg.clear();
	    pct80.clear();
	    pct90.clear();
	    
	    dataset.addSeries(seriesAvg);
	    dataset.addSeries(seriesP80);
	    dataset.addSeries(seriesP90);
	    
	    String xLabel = "Elapsed Time In Test (secs)";
	    String yLabel = "Response Time (secs)";
	    
		jFreeChart = ChartFactory.createXYLineChart(
				imgName.replaceAll(".png", ""), xLabel, yLabel, dataset,
				PlotOrientation.VERTICAL, true, false, false);
		
		paramSetting(jFreeChart, Color.blue, Color.red, Color.green);
		outputPNG(imgDir+File.separator+imgName,jFreeChart);
	}
}

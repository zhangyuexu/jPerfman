package com.jperfman.chart.util;

import java.awt.Color;
import java.io.File;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class MonDataGraph extends Graph {

	public void graphCpu(Map<Integer, Integer> cpuUse,
			Map<Integer, Integer> cpuSys, Map<Integer, Double> cpuLoad,
			String imgName, String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries seriesUse = new XYSeries("CPU_Use");
		XYSeries seriesSys = new XYSeries("CPU_Sys");
		XYSeries seriesLoad = new XYSeries("CPU_Loadaverage");
		
		for (Integer key : cpuUse.keySet()) {
			seriesUse.add(key, cpuUse.get(key));
			seriesSys.add(key, cpuSys.get(key));
			seriesLoad.add(key, cpuLoad.get(key));
		}
		
		cpuUse.clear();
		cpuSys.clear();
		cpuLoad.clear();
		
		dataset.addSeries(seriesUse);
		dataset.addSeries(seriesSys);
		dataset.addSeries(seriesLoad);
		String xLabel = "Elapsed Time In Test (secs)";
		String yLabel = "CPU Information on Top (%)";
		
		jFreeChart = ChartFactory.createXYLineChart("Server CPU Infomation",
				xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, false,
				false);
		super.paramSetting(jFreeChart, Color.green, Color.orange, Color.red);
		super.outputPNG(imgDir + File.separator + imgName, jFreeChart);
	}
	
	public void graphMem(Map<Integer, Integer> free,
			Map<Integer, Integer> buff, Map<Integer, Integer> cache,
			String imgName, String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries seriesFree = new XYSeries("MEM_Free");
		XYSeries seriesBuff = new XYSeries("MEM_Buff");
		XYSeries seriesCache = new XYSeries("MEM_Cache");
		
		for (Integer key : free.keySet()) {
			seriesFree.add(key, free.get(key));
			seriesBuff.add(key, buff.get(key));
			seriesCache.add(key, cache.get(key));
		}
		
		free.clear();
		buff.clear();
		cache.clear();
		
		dataset.addSeries(seriesFree);
		dataset.addSeries(seriesBuff);
		dataset.addSeries(seriesCache);
		String xLabel = "Elapsed Time In Test (secs)";
		String yLabel = "Memory Information on Top (MB)";
		
		jFreeChart = ChartFactory.createXYLineChart("Server Memory Infomation",
				xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, false,
				false);
		super.paramSetting(jFreeChart, Color.green, Color.orange, Color.red);
		super.outputPNG(imgDir + File.separator + imgName, jFreeChart);
	}
	
	public void graphIO(Map<Integer, Integer> bi, Map<Integer, Integer> bo,
			String imgName, String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries seriesBI = new XYSeries("IO_BI");
		XYSeries seriesBO = new XYSeries("IO_BO");
		
		for (Integer key : bi.keySet()) {
			seriesBI.add(key, bi.get(key));
			seriesBO.add(key, bo.get(key));
		}
		
		bi.clear();
		bo.clear();
		
		dataset.addSeries(seriesBI);
		dataset.addSeries(seriesBO);
		String xLabel = "Elapsed Time In Test (secs)";
		String yLabel = "IO Information on Top (Block)";
		
		jFreeChart = ChartFactory.createXYLineChart("Server IO Infomation",
				xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, false,
				false);
		super.paramSetting(jFreeChart, Color.green, Color.red, null);
		super.outputPNG(imgDir + File.separator + imgName, jFreeChart);
	}
	
	public void graphSwap(Map<Integer, Integer> si, Map<Integer, Integer> so,
			String imgName, String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries seriesSI = new XYSeries("SWAP_SI");
		XYSeries seriesSO = new XYSeries("SWAP_SO");
		
		for (Integer key : si.keySet()) {
			seriesSI.add(key, si.get(key));
			seriesSO.add(key, so.get(key));
		}
		
		si.clear();
		so.clear();
		
		dataset.addSeries(seriesSI);
		dataset.addSeries(seriesSO);
		String xLabel = "Elapsed Time In Test (secs)";
		String yLabel = "IO Information on Top (MB)";
		
		jFreeChart = ChartFactory.createXYLineChart("Server SWAP Infomation",
				xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, false,
				false);
		super.paramSetting(jFreeChart, Color.green, Color.red, null);
		super.outputPNG(imgDir + File.separator + imgName, jFreeChart);
	}
	
	public void graphNetIO(Map<Integer, Integer> si, Map<Integer, Integer> so,
			String imgName, String imgDir) {
		JFreeChart jFreeChart;
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries seriesNR = new XYSeries("NET_RCV Per Sec");
		XYSeries seriesNS = new XYSeries("NET_SND Per Sec");
		
		for (Integer key : si.keySet()) {
			seriesNR.add(key, si.get(key));
			seriesNS.add(key, so.get(key));
		}
		
		si.clear();
		so.clear();
		
		dataset.addSeries(seriesNR);
		dataset.addSeries(seriesNS);
		String xLabel = "Elapsed Time In Test (secs)";
		String yLabel = "Net IO Information on Top (KB)";
		
		jFreeChart = ChartFactory.createXYLineChart("Server NET Infomation",
				xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, false,
				false);
		super.paramSetting(jFreeChart, Color.green, Color.red, null);
		super.outputPNG(imgDir + File.separator + imgName, jFreeChart);
	}
}

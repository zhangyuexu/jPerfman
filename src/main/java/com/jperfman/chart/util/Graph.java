package com.jperfman.chart.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.util.ShapeUtilities;

public class Graph {
	
	private int intervalSecs;
	
	private int width;
	private int height;
	
	public void initPicSize(int runTime) {
		int totalSec = runTime * intervalSecs;
		if (totalSec <= 1800) {
			width = 900;
		} else if ((totalSec > 1800) && (totalSec <= 5400)) {
			width = (totalSec / 300 * 150);
			if (width > 1800) {
				width = 1800;
			}
		} else if (totalSec > 5400) {
			width = 1800;
		}
		height = width / 2;
	}
	
	protected void paramSetting(JFreeChart jFreeChart, Color first, Color second, Color third) {
		jFreeChart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) jFreeChart.getPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.GRAY);
		plot.setRangeGridlinePaint(Color.GRAY);
		XYItemRenderer render = plot.getRenderer();
		render.setSeriesPaint(0, Color.blue);
		render.setSeriesShape(0, ShapeUtilities.createDiamond(1.0F));
		render.setSeriesShape(1, ShapeUtilities.createDiamond(1.0F));
		render.setSeriesShape(2, ShapeUtilities.createDiamond(1.0F));

		XYLineAndShapeRenderer xyRenderer = (XYLineAndShapeRenderer) plot
				.getRenderer();
		xyRenderer.setSeriesShapesVisible(0, true);
		xyRenderer.setSeriesShapesVisible(1, true);
		xyRenderer.setSeriesShapesVisible(2, true);
		if (first != null) {
			xyRenderer.setSeriesPaint(0, first, true);
		} else {
			xyRenderer.setSeriesPaint(0, Color.blue, true);
		}
		if (second != null) {
			xyRenderer.setSeriesPaint(1, second, true);
		} else {
			xyRenderer.setSeriesPaint(1, Color.red, true);
		}
		if (third != null) {
			xyRenderer.setSeriesPaint(2, third, true);
		} else {
			xyRenderer.setSeriesPaint(2, Color.green, true);
		}

		ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setAutoRangeMinimumSize(0.003D);

		jFreeChart.getLegend().setItemFont(new Font("宋体", 0, 12));
		jFreeChart.setBorderVisible(true);
	}
	
	public void outputPNG(String fileName, JFreeChart jFreeChart)
    {
      String imgType = "PNG";
      if (fileName.endsWith(".png"))
        imgType = "PNG";
      else if (fileName.endsWith(".jpg")) {
        imgType = "PNG";
      }
      BufferedImage bi = jFreeChart.createBufferedImage(width, height);
      File imgCacheFile = new File(fileName);
      if (imgCacheFile.exists())
        imgCacheFile.delete();
      try
      {
        imgCacheFile.createNewFile();
        ImageIO.write(bi, imgType, imgCacheFile);
      } catch (IOException e) {
        System.out.println(e);
      }
    }
}

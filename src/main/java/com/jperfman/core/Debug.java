package com.jperfman.core;

import java.util.ArrayList;
import java.util.List;

import com.jperfman.chart.impl.MonitorDataCharter;
import com.jperfman.chart.impl.PerfResultCharter;
import com.jperfman.chart.impl.ThreadViewCharter;
import com.jperfman.html.HtmlDriver;
import com.jperfman.util.Const;

public class Debug {

	public static void main(String[] args) {
		//利用性能测试结果进行绘图
		PerfResultCharter perfResultCharter = new PerfResultCharter(
				args[0], args[1],
				Integer.parseInt(args[2]), Const.CHART_STATISTIC_INTERVAL);
		
		long start = System.currentTimeMillis();
		perfResultCharter.init();
		perfResultCharter.start();
		
		List<Thread> threads = new ArrayList<Thread>();
		threads.add(perfResultCharter);
		
		
		// 分析数据生成结果展示页面
		HtmlDriver htmlDriver = new HtmlDriver(args[0], args[1], Const.USER_TIMER,
				Integer.parseInt(args[2]), Const.CHART_STATISTIC_INTERVAL);
		htmlDriver.run();
		
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Time consumed: " + (end - start)/1000);
	}
}

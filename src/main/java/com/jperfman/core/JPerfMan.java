package com.jperfman.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.jperfman.chart.impl.MonitorDataCharter;
import com.jperfman.chart.impl.PerfResultCharter;
import com.jperfman.chart.impl.ThreadViewCharter;
import com.jperfman.config.TestCaseConfig;
import com.jperfman.html.HtmlDriver;
import com.jperfman.monitor.IMonitor;
import com.jperfman.monitor.PyInfoMonitor;
import com.jperfman.monitor.ResourceMonitor;
import com.jperfman.monitor.writer.PyJsonWriter;
import com.jperfman.result.FileSaver;
import com.jperfman.result.IResultSaver;
import com.jperfman.result.Result;
import com.jperfman.result.ResultCollector;
import com.jperfman.util.Const;


public class JPerfMan {
	
	/*
	 * 私有构造函数
	 */
	private JPerfMan()
	{
		
	}
	
	public static void main( String[] args )
    {
        System.out.println( "start to run JPerfMan" );
        //首先读取程序本身的配置文件 (暂无)
//        String configPath = "";
//        String configName = "";
//        JPerfManConfig config = new JPerfManConfig();
//        config.init(configPath, configName);
//        config.load();
        
        if (args.length < 2) {
        	System.out.println("Usage: java -jar jar_package case_path case_name");
        	System.exit(1);
        }
        
        //读取测试用例的配置信息
        String casePath = args[0];
        String caseName = args[1];
        TestCaseConfig caseConfig = new TestCaseConfig(casePath, caseName);
        caseConfig.load();
        //获取测试用例
        TestCaseInfo caseinfo = caseConfig.getTestCaseInfo();
        
        //初始化保存性能数据的队列
        Queue<Result> resultQueue = new ConcurrentLinkedQueue<Result>();
        
        //初始化被测机器资源监听线程
        ResourceMonitor monitorThread = new ResourceMonitor();
        monitorThread.setIsMax();
		IMonitor mon = new PyInfoMonitor(caseinfo.getMonitorIP(),caseinfo.getMonitorPort());
		monitorThread.setMonitor(mon);
		PyJsonWriter wtr = new PyJsonWriter(System.getProperty("user.dir"),Const.MONITOR_RESULT_FILE);
		monitorThread.setWriter(wtr);
		monitorThread.setInterval(Const.MONITOR_INTERVAL);
		monitorThread.start();
        
        //初始化线程数收集线程
        String path = System.getProperty("user.dir") + File.separator + Const.THREAD_NUM_FILE;
        RunnerCounter counter = new RunnerCounter(path,Const.THREAD_COLLECT_INTERVAL);
		
        
        //初始化队列中性能数据读取线程
        ResultCollector collectorThread = new ResultCollector();
        //增加了判断退出逻辑， 暂时去掉后台线程设置
//        collectorThread.setDaemon(true);
        collectorThread.init(resultQueue);
        collectorThread.start();
        
        //构建测试group
        ManualScriptJPerfGroup perfGroup = new ManualScriptJPerfGroup();
        perfGroup.initQueue(resultQueue);
        perfGroup.setCounter(counter);
        perfGroup.loadScript(caseinfo.getScriptType(), caseinfo.getScript());
        
        //测试用testcase  DEBUG用
//        TestCaseInfo caseinfo = new TestCaseInfo();
//        caseinfo.setRuntime(1800000);
//        caseinfo.setRampupMode(Const.RAMPUP_GRAD);
//        caseinfo.setRampup(60000);
//        caseinfo.setThreadInitNum(100);
//        caseinfo.setThreadIncrement(100);
//        caseinfo.setThreadDuration(300000);
//        caseinfo.setThreadTotal(600);
        
        
        //开始执行性能测试压力
        counter.start();
        perfGroup.run(caseinfo);
        
        
        //停止收集线程数目
        counter.setExit();
        //压测执行结束， 停止收集监控信息
        monitorThread.setExit();
        //压测执行结束，通知结果收集线程
        collectorThread.setFinish();
        
        try {
			monitorThread.join();
			counter.join();
			collectorThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        resultQueue = null;
        
        List<Result> list = collectorThread.getList();
        
        //不在进行单独的排序，数据的顺序在绘图前统计成tps等数值
//        MergeSorter sorter = new MergeSorter();
//        List<Result> resultList = sorter.sort(list);
        
        //初始化结果保存实例
        IResultSaver saver = new FileSaver(System.getProperty("user.dir"), Const.PERF_DATA_FILE);
        saver.save(list);
        
        list = null;
        collectorThread = null;
        
        //利用性能测试结果进行绘图
		PerfResultCharter perfResultCharter = new PerfResultCharter(
				System.getProperty("user.dir") + File.separator
						+ Const.PERF_DATA_FILE, System.getProperty("user.dir"),
				caseinfo.getRuntime(), Const.CHART_STATISTIC_INTERVAL);
		MonitorDataCharter monitorDataCharter = new MonitorDataCharter(
				System.getProperty("user.dir") + File.separator
						+ Const.MONITOR_RESULT_FILE,
				System.getProperty("user.dir"), Const.CHART_STATISTIC_INTERVAL);
		ThreadViewCharter threadViewCharter = new ThreadViewCharter(
				System.getProperty("user.dir") + File.separator
						+ Const.THREAD_NUM_FILE,
				System.getProperty("user.dir"), Const.CHART_STATISTIC_INTERVAL);
		
		perfResultCharter.init();
		monitorDataCharter.init();
		threadViewCharter.init();
		perfResultCharter.start();
		monitorDataCharter.start();
		threadViewCharter.start();
		
		List<Thread> threads = new ArrayList<Thread>();
		threads.add(perfResultCharter);
		threads.add(monitorDataCharter);
		threads.add(threadViewCharter);
		
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		perfResultCharter = null;
		monitorDataCharter = null;
		threadViewCharter = null;
		
		
		// 分析数据生成结果展示页面
		HtmlDriver htmlDriver = new HtmlDriver(System.getProperty("user.dir")
				+ File.separator + Const.PERF_DATA_FILE,
				System.getProperty("user.dir"), Const.USER_TIMER,
				caseinfo.getRuntime(), Const.CHART_STATISTIC_INTERVAL);
		htmlDriver.run();
		
//		for (Thread t : threads) {
//			try {
//				t.join();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		

        //perfman 为性能测试总管，负责读取性能测试用例的配置信息
        //perfman.readPerfConfig();
        
        //perfman 根据读取的配置信息，计算应该分配几个进程，并负责启动对应的进程
        //本次设计为单进程，则perfman 只需要生成一个进程，即本进程，参数传递利用api进行
        //一个压测进程对应一个 perfgroup, 以后修改为多进程*多线程，在perfgroup处进行分割
        
        //perfman 根据传递的性能测试用例总的配置信息，进行计算，
        
        
        //perfman
        
        
        
        
        //最后监听的线程
        
        //报告生成
    }
}

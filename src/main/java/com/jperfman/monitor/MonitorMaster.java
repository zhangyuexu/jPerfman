package com.jperfman.monitor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jperfman.chart.impl.MonitorDataAvgCharter;
import com.jperfman.chart.impl.MonitorDataCharter;
import com.jperfman.monitor.writer.PyJsonWriter;

public class MonitorMaster {
	private List<ResourceMonitor> resourceMonitors;
	
	private long startTime;
	
	public MonitorMaster() {
		resourceMonitors = new ArrayList<ResourceMonitor>();
	}
	
	public void addResourceMonitor(ResourceMonitor rm) {
		resourceMonitors.add(rm);
	}
	
	public void setStartTime(long time) {
		this.startTime = time;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public void process() {
		for (ResourceMonitor item : resourceMonitors) {
			item.start();
		}
	}
	
	private class Hooker extends Thread {
		private long endTime;
		
		private String outputDir;
		private String testSceneDir;
		
		public void setOutputDir(String outputDir) {
			this.outputDir = outputDir;
		}
		
		public void setSceneDir(String sceneDir) {
			this.testSceneDir = sceneDir;
		}
		
		public void run() {
			endTime = System.currentTimeMillis();
			long runTime = (endTime - startTime) / 1000;
			String fileName = "analyse_all.log.";
			List<Thread> threads = new ArrayList<Thread>();
			
			List<String> allAnalyseFiles = new ArrayList<String>();
			
			String baseDirName = outputDir + File.separator + testSceneDir;
			File baseDir = new File(baseDirName);
			if (!baseDir.exists()) {
				baseDir.mkdir();
			}
			
			for (ResourceMonitor item : resourceMonitors) {
				String host = item.getMonitor().getHost();
				File dir = new File(baseDirName + File.separator + host);
				dir.mkdir();
				String subOutputDir = baseDirName + File.separator + host;
				MonitorDataCharter chart = new MonitorDataCharter(outputDir
						+ File.separator + fileName + host, subOutputDir,
						(int) runTime);
				chart.init();
				chart.start();
				threads.add(chart);
				
				allAnalyseFiles.add(item.getWriter().getOutputFullName());
				
				//结束ResourceMonitor线程
				item.setExit();
			}
			
			//计算多个服务器监控资源的平均值图表
			File dir = new File(baseDirName + File.separator + "Average");
			dir.mkdir();
			String avgOutputDir = baseDirName + File.separator + "Average";
			MonitorDataAvgCharter avgCharter = new MonitorDataAvgCharter(allAnalyseFiles,avgOutputDir,(int)runTime);
			avgCharter.init();
			avgCharter.start();
			
			for (Thread t : threads) {
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				avgCharter.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void hook(String outputDir, String testScene) {
		Hooker hooker = new Hooker();
		hooker.setOutputDir(outputDir);
		hooker.setSceneDir(testScene);
		Runtime.getRuntime().addShutdownHook(hooker);
		
//		hooker.run();
	}
	
	public static void Usage() {
		System.out
				.println("java -cp <app.jar> com.jperfman.monitor.MonitorMaster <interval> <outputdir> <testscene> <host:port>...\n"
						+ "\tinterval  Interval between resource info monitored\n"
						+ "\toutputdir Dir for storing monitor info and analyzed graph\n"
						+ "\ttestscene TestScene for differing, for instance wordcount, or sort etc."
						+ "\thost:port Host IP for monitor, and monitor agent's port (Note: could be many host:port for monitoring cluster)");
	}
	
	
	public static void main(String[] args) {
		if (args.length < 4) {
			Usage();
			System.exit(-1);
		}
		String fileName = "analyse_all.log.";
		String outputDir = args[1];
		String testScene = args[2];
		int interval = Integer.parseInt(args[0]);
		MonitorMaster master = new MonitorMaster();
//		IParser par = new PyJsonParser();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String tmpDate = df.format(new Date());
		String sceneFinal = testScene + "_" + tmpDate;
		
		
		for (int i=3;i<args.length;i++) {
			ResourceMonitor demo = new ResourceMonitor();
			String[] cols = args[i].split(":");
			String perHostAnalysisFile = outputDir + File.separator + fileName + cols[0];
			File perHostFile = new File(perHostAnalysisFile);
			if (perHostFile.exists()) {
				perHostFile.delete();
			}
			IMonitor mon = new PyInfoMonitor(cols[0], cols[1]);
			PyJsonWriter wtr = new PyJsonWriter(outputDir,fileName+cols[0]);
			demo.setInterval(interval);
//			demo.setPareser(par);
			demo.setMonitor(mon);
			demo.setWriter(wtr);
			master.addResourceMonitor(demo);
		}
		
		master.hook(outputDir,sceneFinal);
		master.setStartTime(System.currentTimeMillis());
		master.process();
		
//		try {
//			Thread.sleep(25000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//DEBUG
//		master.hook(outputDir,sceneFinal);
	}
}

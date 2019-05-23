package com.jperfman.monitor;


import com.jperfman.exception.MonitorException;
import com.jperfman.monitor.writer.PyJsonWriter;
import com.jperfman.util.Const;

public class ResourceMonitor extends Thread {
	private int interval;
	private boolean exitFlag = false;
	private boolean isMaxFlag = false;
	
//	private IParser parser;
	private IMonitor monitor;
	private PyJsonWriter writer;
	
	public long startTime;
	
	public void setExit() {
		exitFlag = true;
	}
	
	public void setIsMax() {
		isMaxFlag = true;
	}
	
	public void setInterval(int secs) {
		this.interval = secs * 1000;
	}
	
	public void setMonitor(IMonitor monitor) {
		this.monitor = monitor;
	}
	
//	public void setPareser(IParser parser) {
//		this.parser = parser;
//	}
	
	public void setWriter(PyJsonWriter writer) {
		this.writer = writer;
	}
	
	public IMonitor getMonitor() {
		return monitor;
	}
	
	public PyJsonWriter getWriter() {
		return writer;
	}
	
	public void run() {
		while (true) {
			try {
				long startTime = System.currentTimeMillis();
				String monitorData = monitor.monitor();
//				MonitorInfoEntity monitorInfo = parser.parse(monitorData);
				writer.writeInfo(monitorData);
				int usedTime = (int)(System.currentTimeMillis() - startTime);
				
				if (usedTime < interval) {
					Thread.sleep(interval - usedTime);
				}
				
				if (exitFlag) {
					if (isMaxFlag) {
						writer.saveMax();
					}
					return;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MonitorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ResourceMonitor demo = new ResourceMonitor();

		IMonitor mon = new PyInfoMonitor("172.18.1.65", "18085");
		PyJsonWriter wtr = new PyJsonWriter(System.getProperty("user.dir"), Const.MONITOR_RESULT_FILE);
		demo.setMonitor(mon);
		demo.setWriter(wtr);
		demo.setInterval(2);

		demo.start();
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		demo.setExit();
		
		try {
			demo.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.jperfman.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class RunnerCounter extends Thread {

	private int runnerNum;
	
	private int interval = 1;
	
	private String filePath;
	
	private boolean exitFlag;
	
	public RunnerCounter(String filePath, int interval) {
		this.filePath = filePath;
		this.interval = interval;
		this.runnerNum = 0;
		this.exitFlag = false;
	}
	
	public void addCount(int num) {
		runnerNum += num;
	}
	
	public void setExit() {
		this.exitFlag = true;
	}
	
	public void run() {
		int secs = 0;
		File file = new File(filePath);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter os = new OutputStreamWriter(fos);
			BufferedWriter out = new BufferedWriter(os);
			
			while (!this.exitFlag) {
				Thread.sleep(interval * 1000);
				out.write(String.valueOf(secs) + "," + String.valueOf(runnerNum) + "\n");
//				Thread.sleep(interval * 1000);
				secs += interval;
			}
			
			out.write(String.valueOf(secs) + "," + String.valueOf(runnerNum) + "\n");
			out.flush();
			out.close();
			os.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

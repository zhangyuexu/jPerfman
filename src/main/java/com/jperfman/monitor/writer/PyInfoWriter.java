package com.jperfman.monitor.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jperfman.monitor.MonitorInfoEntity;

public class PyInfoWriter implements IWriter {

	private String outputDir;
	private String outputName;
	
	private static final String BACKLOG = "backup_cpuinfo.log";
	private static final String MAXLOG = "analyse_max.log";
	private static final String ALLLOG = "analyse_all.log";
	
	public PyInfoWriter(String outputDir, String outputName) {
		this.outputDir = outputDir;
		this.outputName = outputName;
	}
	
	@Override
	public void writeInfo(MonitorInfoEntity monitorInfo) {
		// TODO Auto-generated method stub
//		File backupLog = new File(outputDir + File.separator + BACKLOG);
//		File allLog = new File(outputDir + File.separator + ALLLOG);
		File outLog = new File(outputDir + File.separator + outputName);
		
		try {
//			FileOutputStream outBackup = new FileOutputStream(backupLog, true);
//			outBackup.write(monitorInfo.toBackUpLog().getBytes());
//			outBackup.close();
//			
//			FileOutputStream outAll = new FileOutputStream(allLog, true);
//			outAll.write(monitorInfo.toAllLog().getBytes());
//			outAll.close();
			
			FileOutputStream out = new FileOutputStream(outLog, true);
			out.write(monitorInfo.toAllLog().getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.jperfman.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.jperfman.result.ResultAnalysis;
import com.jperfman.util.Const;

public class HtmlDriver {
	
	private String fileName;
	private String outputDir;
	private String userTimer;
	private int runTime;
	private int interval;
	
	public HtmlDriver(String fileName, String outputDir, String userTimer, int runTime, int interval) {
		this.fileName = fileName;
		this.outputDir = outputDir;
		this.userTimer = userTimer;
		this.runTime = runTime;
		this.interval = interval;
	}
	
	public void generate(ResultAnalysis ra) {
		File file = new File(outputDir + File.separator + Const.RESULT_PAGE);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			new ResultPage().render(new OutputStreamWriter(fos), ra, outputDir);
			
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		ResultAnalysis tmp = new ResultAnalysis(fileName,outputDir,userTimer,runTime,interval);
		tmp.parse();
		this.generate(tmp);
	}
	
	public static void main(String[] args) {
		String name = "E:\\MyWork\\TestTeamSVN\\perfplantform\\jperfman\\results.csv";
		String dir = "E:\\MyWork\\TestTeamSVN\\perfplantform\\jperfman";
		HtmlDriver demo = new HtmlDriver(name,dir,"user_timer",1800,5);
		demo.run();
	}
}

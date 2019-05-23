package com.jperfman.result;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.jperfman.result.analyzer.ScriptTimeAnalyzer;
import com.jperfman.result.analyzer.ScriptTimeAnalyzerEx;
import com.jperfman.result.analyzer.ScriptTimeDetailAnalyzer;
import com.jperfman.result.analyzer.ScriptTimeDetailAnalyzerEx;
import com.jperfman.result.analyzer.SimpleAnalyzer;
import com.jperfman.result.analyzer.ThreadTpsAnalyzer;
import com.jperfman.result.analyzer.UserTimeAnalyzer;
import com.jperfman.result.analyzer.UserTimeAnalyzerEx;
import com.jperfman.result.analyzer.UserTimeDetailAnalyzer;
import com.jperfman.result.analyzer.UserTimeDetailAnalyzerEx;

public class ResultAnalysis {
	private ExecutorService execService;

	private String fileName;
	private String fileDir;
	private String userTimer;
	private int runTime;
	private int interval;
	
	private int totalTransactions;
	private int totalErrors;
	private String startTime;
	private String endTime;
	
	private Map<String,Double> scriptTimeSummary;
	private Map<String,Double> userTimeSummary;
	private Map<Integer,List<Double>> scriptTimeDetail;
	private Map<Integer,List<Double>> userTimeDetail;
	private Map<Integer,List<Integer>> threadTpsResp;
	
	public ResultAnalysis(String fileName, String fileDir, String userTimer, int runTime, int interval) {
		this.fileName = fileName;
		this.fileDir = fileDir;
		this.userTimer = userTimer;
		this.runTime = runTime;
		this.interval = interval;
		this.totalErrors = 0;
		this.totalTransactions = 0;
		
		execService = Executors.newFixedThreadPool(5);
	}
	
	public void parse() {
		SimpleAnalyzer simpleAnalyzer = new SimpleAnalyzer(fileName);
		
//		ScriptTimeAnalyzer scriptTimeAnalyzer = new ScriptTimeAnalyzer(fileName,runTime,interval);
//		ScriptTimeDetailAnalyzer scriptTimeDetailAnalyzer = new ScriptTimeDetailAnalyzer(fileName,runTime,interval);
//		UserTimeAnalyzer userTimeAnalyzer = new UserTimeAnalyzer(fileName,runTime,interval,userTimer);
//		UserTimeDetailAnalyzer userTimeDetailAnalyzer = new UserTimeDetailAnalyzer(fileName,runTime,interval,userTimer);
		ScriptTimeAnalyzerEx scriptTimeAnalyzer = new ScriptTimeAnalyzerEx(fileName,runTime,interval);
		ScriptTimeDetailAnalyzerEx scriptTimeDetailAnalyzer = new ScriptTimeDetailAnalyzerEx(fileName,runTime,interval);
		UserTimeAnalyzerEx userTimeAnalyzer = new UserTimeAnalyzerEx(fileName,runTime,interval,userTimer);
		UserTimeDetailAnalyzerEx userTimeDetailAnalyzer = new UserTimeDetailAnalyzerEx(fileName,runTime,interval,userTimer);
		ThreadTpsAnalyzer threadTpsAnalyzer = new ThreadTpsAnalyzer(fileName,runTime,interval,fileDir,userTimer);
		
		simpleAnalyzer.start();
		Future<Map<String,Double>> future1 = execService.submit(scriptTimeAnalyzer);
		Future<Map<Integer,List<Double>>> future2 = execService.submit(scriptTimeDetailAnalyzer);
		Future<Map<String,Double>> future3 = execService.submit(userTimeAnalyzer);
		Future<Map<Integer,List<Double>>> future4 = execService.submit(userTimeDetailAnalyzer);
		Future<Map<Integer,List<Integer>>> future5 = execService.submit(threadTpsAnalyzer);
		
		try {
			simpleAnalyzer.join();
			this.totalTransactions = simpleAnalyzer.getTotalTransactions();
			this.totalErrors = simpleAnalyzer.getTotalErrors();
			this.startTime = simpleAnalyzer.getStartTime();
			this.endTime = simpleAnalyzer.getEndTime();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.scriptTimeSummary = future1.get();
			this.scriptTimeDetail = future2.get();
			this.userTimeSummary = future3.get();
			this.userTimeDetail = future4.get();
			this.threadTpsResp = future5.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		execService.shutdown();
	}
	
	public int getTotalTransactions() {
		return this.totalTransactions;
	}
	
	public int getTotalErrors() {
		return this.totalErrors;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public int getRunTime() {
		return runTime;
	}
	
	public int getInterval() {
		return interval;
	}
	
	public String getUserTimer() {
		return userTimer;
	}
	
	public Map<String,Double> getScriptTimeSummary() {
		return this.scriptTimeSummary;
	}
	
	public Map<String,Double> getUserTimeSummary() {
		return this.userTimeSummary;
	}
	
	public Map<Integer,List<Double>> getScriptTimeDetail() {
		return this.scriptTimeDetail;
	}
	
	public Map<Integer,List<Double>> getUserTimeDetail() {
		return this.userTimeDetail;
	}
	
	public Map<Integer,List<Integer>> getThreadTpsResp() {
		return threadTpsResp;
	}
	
	public static void main(String[] args) {
		String filename = "E:\\MyWork\\TestTeamSVN\\perfplantform\\jperfman\\result.csv";
		ResultAnalysis tmp = new ResultAnalysis(args[0], args[1],"user_timer",Integer.parseInt(args[2]),5);
		long start = System.currentTimeMillis();
		tmp.parse();
		long end = System.currentTimeMillis();
		System.out.println("Time consued: " + (end - start)/1000);
		System.out.println(tmp.getThreadTpsResp());
		System.out.println(tmp.getTotalErrors());
		System.out.println(tmp.getTotalTransactions());
		System.out.println(tmp.getStartTime());
		System.out.println(tmp.getEndTime());
		System.out.println(tmp.getScriptTimeSummary());
		System.out.println(tmp.getUserTimeSummary());
		System.out.println(tmp.getScriptTimeDetail());
		System.out.println(tmp.getUserTimeDetail());
	}
}

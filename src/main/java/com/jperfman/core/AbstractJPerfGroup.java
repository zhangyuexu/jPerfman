package com.jperfman.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.jperfman.result.Result;
import com.jperfman.util.Const;

public abstract class AbstractJPerfGroup {
	private final String groupName = "JPerfGroup";
	
	private List<Thread> threadList = new ArrayList<Thread>();
	
	private Queue<Result> resultQueue;
	
	private RunnerCounter counter;
	
	protected Class scriptClazz;
	
	public void initQueue(Queue<Result> resultQueue) {
		this.resultQueue = resultQueue;
	}
	
	public void setCounter(RunnerCounter counter) {
		this.counter = counter;
	}

	public void run(TestCaseInfo caseInfo) {
		int runTime = caseInfo.getRuntime();
		int rampupMode = caseInfo.getRampupMode();
		int rampup = caseInfo.getRampup();
		int threadInitNum = caseInfo.getThreadInitNum();
		int threadIncrement = caseInfo.getThreadIncrement();
		int threadDuration = caseInfo.getThreadDuration();
		int threadTotal = caseInfo.getThreadTotal();
		
		if (rampupMode == Const.RAMPUP_GRAD) {
			List<Integer> valGrad = buildGrad(threadInitNum, threadIncrement,
					threadDuration, runTime, threadTotal);
			
			for (Integer i : valGrad) {
				System.out.println("DEBUG: Grad array " + i);
			}
			rampupGrad(valGrad, runTime, threadDuration);
		} else if (rampupMode == Const.RAMPUP_LINE) {
			rampupLinear(threadTotal, rampup, runTime);
		}
		
		//等待所有线程执行结束
		for (Thread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void rampupLinear(int threadTotal, int rampup, int runTime) {
		long startTime = System.currentTimeMillis();
		int spacing = rampup / threadTotal;
		
		for (int i=0;i<threadTotal;i++) {
			if (i > 0) {
				try {
					Thread.sleep(spacing);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			JPerfRunner jperfRunner = new JPerfRunner(groupName,startTime,runTime);
			jperfRunner.setResultQueue(resultQueue);
			jperfRunner.registerScript(scriptClazz);
			threadList.add(jperfRunner);
			jperfRunner.start();
			
			counter.addCount(1);
		}
	}
	
	public void rampupGrad(List<Integer> valGrad, long runTime, int threadDuration) {
		long startTime = System.currentTimeMillis();
		
		for (Integer val : valGrad) {
			counter.addCount(val);
			for (int i=0;i<val;i++) {
//				System.out.println("rampupGrad: start a jperfrunner");
				JPerfRunner jperfRunner = new JPerfRunner(groupName,startTime,runTime);
				jperfRunner.setResultQueue(resultQueue);
				jperfRunner.registerScript(scriptClazz);
				threadList.add(jperfRunner);
				jperfRunner.start();
			}
			
//			counter.addCount(val);
			
			try {
				Thread.sleep(threadDuration);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private List<Integer> buildGrad(int threadInitNum, int threadIncrement,
			int threadDuration, int runTime, int threadTotal) {
		List<Integer> arr = new ArrayList<Integer>();
		arr.add(threadInitNum);

		// 执行总时长 <= 每段线程并发的时长 || 初始化线程并发数 >= 总线程数
		if (runTime <= threadDuration || threadInitNum >= threadTotal) {
			return arr;
		}
		
		// 分别按时间段间隔 和 线程并发增量 计算梯度的次数，取最小的次数
		int cntGradFromDuration = runTime / threadDuration - 1;
		int cntGradFromThread = (threadTotal - threadInitNum) / threadIncrement;
		
		int count = Math.min(cntGradFromDuration, cntGradFromThread);
		
		for (int i=0;i<count;i++) {
			arr.add(threadIncrement);
		}
		
		

		return arr;
	}
	
//	public abstract void loadScript();
}

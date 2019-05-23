package com.jperfman.core;

import java.util.Queue;

import com.jperfman.result.Result;
import com.jperfman.script.ScriptDefine;

public class JPerfRunner extends Thread {
	
	private Queue<Result> resultQueue;
	
	private long startTime;
	
	private long runTime;
	
	private String jperfGroupName;

	private ScriptDefine scriptInstance;
	
	public JPerfRunner(String jperfGroupName, long startTime, long runTime) {
		this.jperfGroupName = jperfGroupName;
		this.startTime = startTime;
		this.runTime = runTime;
	}
	
	public void setResultQueue(Queue<Result> resultQueue) {
		this.resultQueue = resultQueue;
	}
	
	public void registerScript(Class scriptClazz) {
		try {
			scriptInstance = (ScriptDefine) scriptClazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		if (scriptInstance == null) {
			System.out
					.println("[ERROR]: script which loaded into program is NULL");
			return;
		}

		long elapsed = 0;
		
		//setup phrase
		scriptInstance.setup();

		do {
//		while (elapsed < runTime) {
//			System.out.println("runner per loop");
			long startPerloop = System.currentTimeMillis();
			String error = "";
			
			try {
				scriptInstance.runtest();
			} catch (Exception e) {
				e.printStackTrace();
				error = e.getMessage();
				if (error != null) {
					error = error.replaceAll("\n", " ");
					error = error.replaceAll(",", ";");
				}	
			}

			long finishPerloop = System.currentTimeMillis();
			long scriptRunTime = finishPerloop - startPerloop;
			elapsed = System.currentTimeMillis() - startTime;

			long epoch = System.currentTimeMillis();

//			String error = ""; // 用来保存在执行测试脚本过程中捕获的异常，应该更新到runtest()逻辑附近

			resultQueue.add(new Result(this.jperfGroupName, elapsed, epoch,
					scriptRunTime, scriptInstance.data, error));
			
			scriptInstance.clean();
		} while (elapsed <= runTime);
		
		//clean up phrase
		scriptInstance.clean();
	}
}

package com.jperfman.result;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ResultCollector extends Thread {

	private Queue<Result> resultQueue;

	private List<Result> list;
	
	private boolean finishFlag;

	public void init(Queue<Result> resultQueue) {
		this.resultQueue = resultQueue;
		this.finishFlag = false;
		this.list = new ArrayList<Result>();
	}
	
	public void setFinish() {
		this.finishFlag = true;
	}
	
	public List<Result> getList() {
		return list;
	}

	public void run() {
		System.out.println("resultcollector run");

		while (true) {
			try {
				if (finishFlag && resultQueue.size() == 0) {
					break;
				}
				// System.out.println("collector per loop");
				Result result = resultQueue.remove();
				list.add(result);
//				saver.saveResult(result);
				// System.out.println(result);
			} catch (NoSuchElementException e) {
				// e.printStackTrace();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}

	}
}

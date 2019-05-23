package com.jperfman.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jperfman.result.Result;

public class MergeSorter {

	
	public List<Result> sort(List<Result> list) {
		if (list.size() <= Const.SORT_UNIT_CUT) {
//			QuickSorter.qsort(list, 0, list.size()-1);
			HeapSorter.heapSort(list);
			return list;
		} else {
			int realNum;
			int num = list.size() / Const.SORT_UNIT_CUT;
			boolean tailFlag = false;
			
			if (list.size() % Const.SORT_UNIT_CUT != 0) {
				realNum = num + 1;
				tailFlag = true;
			}else {
				realNum = num;
			}
			List<Result>[] splits = new List[realNum];
			for (int i=0;i<num;i++) {
				splits[i] = new ArrayList<Result>();
				for (int j=0;j<Const.SORT_UNIT_CUT;j++) {
					splits[i].add(list.get(j+i*Const.SORT_UNIT_CUT));
				}
//				QuickSorter.qsort(splits[i], 0, Const.SORT_UNIT_CUT-1);
				HeapSorter.heapSort(splits[i]);
				splits[i].add(new Result("tail",0,Const.MAX,0,null,null));
			}
			
			//copy the tail split, because it is less than SORT_UNIT_CUT
			if (tailFlag) {
				splits[realNum-1] = new ArrayList<Result>();
				int lenLeft = list.size()-num*Const.SORT_UNIT_CUT;
				for (int j=0;j<lenLeft;j++) {
					splits[realNum-1].add(list.get(j+lenLeft));
				}
//				QuickSorter.qsort(splits[realNum-1], 0, lenLeft-1);
				HeapSorter.heapSort(splits[realNum-1]);
				splits[realNum-1].add(new Result("tail",0,Const.MAX,0,null,null));
			}
			
			return mergeSort(splits, realNum);
		}
	}
	
	private List<Result> mergeSort(List<Result>[] splits, int num) {
		int[] pos = new int[num];
		long data;
		int i, min;
		List<Result> result = new ArrayList<Result>();
		
		for (int j=0;j<num;j++) {
			pos[j] = 0;
		}
		
		while (true) {
			i = 0;
			while (i < num && splits[i].get(pos[i]).getEpoch() == Const.MAX) {
				i++;
			}
			if (i == num) {
				break;
			}
			min = i;
			data = splits[min].get(pos[min]).getEpoch();
			i++;
			while (i < num) {
				if (splits[i].get(pos[i]).getEpoch() < data) {
					min = i;
					data = splits[min].get(pos[min]).getEpoch();
				}
				i++;
			}
			result.add(splits[min].get(pos[min]));
			pos[min]++;
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		int len = Const.SORT_UNIT_CUT + 1;
		List<Result> list = new ArrayList<Result>();
		Random rand = new Random(894219174893L);
		
		for (int i=0;i<len;i++) {
			list.add(new Result("test",0,rand.nextLong(),0,null,null));
		}
		List<Result> result = new MergeSorter().sort(list);
		
		for (Result i : result) {
			System.out.println(i.getEpoch());
		}
		
		System.out.println(result.size());
	}
}

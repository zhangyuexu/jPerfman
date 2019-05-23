package com.jperfman.util;

import java.util.List;

import com.jperfman.result.Result;

public class HeapSorter {
	
	public static void heapSort(List<Result> list) {
		int len = list.size();
		int i;
		
		for (i=len/2;i>=0;i--) {
			heapAdjust(list, i, len);
		}
		
		for (i=len-1;i>0;i--) {
			Result tmp = list.get(0);
			list.set(0, list.get(i));
			list.set(i, tmp);
			
			heapAdjust(list,0,i);
		}
	}
	
	private static void heapAdjust(List<Result> list, int i, int len) {
		int biggerChild = 0;
		Result tmp;
		
		for (; 2*i+1<len;i=biggerChild) {
			biggerChild = 2 * i + 1;
			if (biggerChild < len - 1 && list.get(biggerChild+1).getEpoch() > list.get(biggerChild).getEpoch()) {
				biggerChild++;
			}
			if (list.get(i).getEpoch() < list.get(biggerChild).getEpoch()) {
				tmp = list.get(biggerChild);
				list.set(biggerChild, list.get(i));
				list.set(i, tmp);
			} else {
				break;
			}
		}
	}
}

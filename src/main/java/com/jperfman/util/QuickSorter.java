package com.jperfman.util;

import java.util.List;

import com.jperfman.result.Result;

public class QuickSorter {
	
	public static void qsort(List<Result> list, int begin, int end) {
		int i = begin;
		int j = end;
		long pivot;
		Result tmp;
		
		if (begin >= end) {
			return;
		}
		pivot = list.get(i).getEpoch();
		tmp = list.get(i);
		
		while (i != j) {
			while (i < j && list.get(j).getEpoch() >= pivot) {
				j--;
			}
			if (i < j) {
				Result small = list.get(j);
				list.set(i, small);
			}
			while (i < j && list.get(i).getEpoch() <= pivot) {
				i++;
			}
			if (i < j) {
				Result big = list.get(i);
				list.set(j, big);
			}
		}
		list.set(i, tmp);
		
		qsort(list, begin, i-1);
		qsort(list, i+1, end);
	}
}

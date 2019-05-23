package com.jperfman.util;

@Deprecated
public class ClassGeter {
	
	public static<T> T getInstance(Class<T> clazz) {
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}

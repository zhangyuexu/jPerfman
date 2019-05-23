package com.jperfman.util;

import java.util.HashMap;

@Deprecated
public class JperfMap<Object, Object1> extends HashMap<Object, Object1> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -88984935864929173L;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		int len = super.size();
		int cnt = 0;
		
		for (Object key : super.keySet()) {
			cnt++;
			sb.append(key);
			sb.append(":");
			sb.append(super.get(key));
			if (cnt < len) {
				sb.append(",");
			}
		}
		sb.append("}");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		JperfMap<String, String> demo = new JperfMap<String,String>();
		
		demo.put("first", "hello");
		demo.put("second", "world");
		
		System.out.println(demo.toString());
	}
}

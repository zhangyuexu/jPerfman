package com.jperfman.result;

import java.io.File;
import java.io.IOException;

@Deprecated
public class ResultDrawer {

	private String result = System.getProperty("user.dir") + File.separator
			+ "result.csv";

	public void run() {
		try {
			Process proc = Runtime.getRuntime().exec(
					"java -jar " + System.getProperty("user.dir")
							+ File.separator + "JChart0324.jar " + result
							+ " 1 1800 1");
//			System.out.println(proc);
			proc.waitFor();
			int exit = proc.exitValue();
			byte[] out = new byte[1024*4];
			proc.getInputStream().read(out);
			System.out.println(exit);
			System.out.println(new String(out, "UTF-8"));
			proc.getErrorStream().read(out);
			System.out.println(new String(out));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ResultDrawer demo = new ResultDrawer();
		demo.run();
	}
}

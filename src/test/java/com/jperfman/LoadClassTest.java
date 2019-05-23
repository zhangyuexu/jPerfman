package com.jperfman;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.jperfman.script.ScriptDefine;

public class LoadClassTest {

	public void test() {
		String path = System.getProperty("user.dir");
		String path2 = path + "\\target\\classes";
		File clazzpath = new File(path2);
		System.out.println(path2);
		
//		try {
//			Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
//			boolean accessible = method.isAccessible();
//			
//			if (accessible == false) {
//				method.setAccessible(true);
//			}
//			
//			URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
//			method.invoke(classloader, clazzpath.toURI().toURL());
//			
//		} catch (NoSuchMethodException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (SecurityException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			URL url = new URL("file: " + path2);
			URLClassLoader classloader = new URLClassLoader(new URL[] {url});
			String subpath = "com.jperfman.script.TestStub";
			
			Class t = classloader.loadClass(subpath);
//			Class t = Class.forName(subpath);
			ScriptDefine aa = (ScriptDefine) t.newInstance();
			aa.runtest();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		LoadClassTest a = new LoadClassTest();
		a.test();
	}
}

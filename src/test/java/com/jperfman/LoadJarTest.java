package com.jperfman;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.jperfman.script.ScriptDefine;

public class LoadJarTest {

	public void test() {
		String path2 = "E:\\MyWork\\TestTeamSVN\\monitor\\eagle_watcher\\eagle_proxy\\target";
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
			Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			addUrl.setAccessible(true);
			URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			addUrl.invoke(classloader, new File(path2).toURI().toURL());
			URL data = classloader.findResource("META-INF.NOTICE");
			InputStream ins = data.openStream();
			byte[] buff = new byte[1024];
			ins.read(buff);
			System.out.println(buff.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		LoadJarTest a = new LoadJarTest();
		a.test();
	}
}

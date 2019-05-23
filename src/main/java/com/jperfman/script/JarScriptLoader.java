package com.jperfman.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import com.jperfman.exception.JarManifestNotFoundException;

public class JarScriptLoader extends AbstractScriptLoader {
	private final static String MANIFEST = "manifest";
	
	public JarScriptLoader() {
		method = initAddMethod();
	}

	public Class load(String path) throws JarManifestNotFoundException {
		// TODO Auto-generated method stub
		File rootPath = new File(path);
		String clazzName = null;
		Class clazz = null;
		
		String manifestPath = path + File.separator + MANIFEST;
		File manifestFile = new File(manifestPath);
		if (manifestFile.exists()) {
			try {
				FileInputStream is = new FileInputStream(manifestFile);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader in = new BufferedReader(isr);
				
				clazzName = in.readLine();
				
				in.close();
				isr.close();
				is.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new JarManifestNotFoundException();
		}
		
		if (rootPath.exists() && rootPath.isDirectory()) {
			File[] classFiles = rootPath.listFiles();
			
			for (File subFile : classFiles) {	
				if (subFile.getName().endsWith(".jar")) {
					try {
						method.invoke(classloader, subFile.toURI().toURL());
//						clazz = classloader.loadClass(clazzName);
						clazz = Class.forName(clazzName, false, classloader);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return clazz;
	}

//	public ScriptDefine initScript(String className) {
//		// TODO Auto-generated method stub
//		try {
//			Class clazz = Class.forName(className);
//			ScriptDefine obj = (ScriptDefine) clazz.newInstance();
//			return obj;
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	
	public static void main(String[] args) {
//		String path = System.getProperty("user.dir");
//		path = path + "\\target\\haha";
//		System.out.println(path);
//		
//		JarScriptLoader demo = new JarScriptLoader();
//		demo.loadJar(path);
//		ScriptDefine test = demo.initScript("TestStub");
//		try {
//			test.runtest();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}

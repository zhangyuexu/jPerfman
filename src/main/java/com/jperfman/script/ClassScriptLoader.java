package com.jperfman.script;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class ClassScriptLoader extends AbstractScriptLoader {
	private final static String LIB_PATH = "lib";

	public ClassScriptLoader() {
		method = initAddMethod();
	}
	
	private void addLibJars(File path) {
		File[] jarFiles = path.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".jar") || name.endsWith(".zip");
			}
			
		});
		
		if (jarFiles != null) {
			for (File file : jarFiles) {
				try {
					URL url = file.toURI().toURL();
					method.invoke(classloader, url);
//					System.out.println("Read jar success: " + file.getName());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public Class load(String path) {
		// TODO Auto-generated method stub
		Class clazz = null;
		File rootPath = new File(path);
		System.out.println(rootPath.getName());
		addPathToClassloader(path);
		
		if (rootPath.exists() && rootPath.isDirectory()) {
			File[] classFiles = rootPath.listFiles();
			
			for (File subFile : classFiles) {
//				System.out.println(subFile.getName());
				if (subFile.isDirectory() && subFile.getName().equalsIgnoreCase(LIB_PATH)) {
					this.addLibJars(subFile);
				}
					
				if (!subFile.isDirectory() && subFile.getName().endsWith(".class")) {
					String className = subFile.getName();
					// 将文件名去掉最后的.class内容
					className = className.substring(0, className.length() - 6);
					try {
						clazz = classloader.loadClass(className);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
			
			return clazz;
			
//			try {
//				return (ScriptDefine) clazz.newInstance();
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		String path = System.getProperty("user.dir") + File.separator + "prac\\http";
		
		ClassScriptLoader demo = new ClassScriptLoader();
		ScriptDefine test = null;
		try {
			test = (ScriptDefine) demo.load(path).newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			test.runtest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

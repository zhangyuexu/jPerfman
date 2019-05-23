package com.jperfman.script;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AbstractScriptLoader {
	protected URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
	
	protected Method method;
	
	protected Method initAddMethod() {
		Method addUrl = null;
		
		try {
			addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addUrl.setAccessible(true);
		return addUrl;
	}
	
	protected void addPathToClassloader(String path) {
		File filePath = new File(path);
		try {
			method.invoke(classloader, filePath.toURI().toURL());
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
		}
	}
}

package com.jperfman.exception;

public class JarManifestNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 378502678836013346L;
	
	public JarManifestNotFoundException() {
		super("Jperfman Jar type script's manifest file not found!");
	}
}

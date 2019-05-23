package com.jperfman;


/**
 * Hello world!
 *
 */
public class App extends Thread
{
	public void run() {
		System.out.println("hook exec");
	}
	
    public static void main( String[] args )
    {
    	App app = new App();
    	Runtime.getRuntime().addShutdownHook(app);
    	while (true) {
    		
    	}
    }
}

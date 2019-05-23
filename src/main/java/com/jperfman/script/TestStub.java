package com.jperfman.script;


import com.jperfman.util.JperfMap;


public class TestStub extends ScriptDefine {

	public TestStub() {
		data = new JperfMap<String, String>();
	}
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runtest() throws Exception {
//		Random random = new Random();
//		int i = random.nextInt(10);
//		if (i < 3) {
//			throw new Exception("My own defined exception");
//		}
		
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		data.put("user_timer", String.valueOf((double)(end - start) / 1000));
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

}

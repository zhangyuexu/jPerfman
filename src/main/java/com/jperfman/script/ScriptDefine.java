package com.jperfman.script;

import java.util.Map;


public abstract class ScriptDefine {
	
	public Map<String,String> data;
	
	public abstract void setup();
	
	public abstract void runtest() throws Exception;
	
	public abstract void clean();
	
	// 不加获取结果时间接口的话， 没有很好的办法； 申明的data变量是静态的
}

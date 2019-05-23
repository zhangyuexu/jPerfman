package com.jperfman.core;

import com.jperfman.exception.JarManifestNotFoundException;
import com.jperfman.script.ClassScriptLoader;
import com.jperfman.script.JarScriptLoader;
import com.jperfman.util.Const;

public class ManualScriptJPerfGroup extends AbstractJPerfGroup {

	public void loadScript(int type, String path) {
		// TODO Auto-generated method stub
		if (type == Const.SCRIPT_CLASS) {
			super.scriptClazz = new ClassScriptLoader().load(path);
//			super.script = new TestStub();
		}
		
		if (type == Const.SCRIPT_JAR) {
			try {
				super.scriptClazz = new JarScriptLoader().load(path);
			} catch (JarManifestNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

}

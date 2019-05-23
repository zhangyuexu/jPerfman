package com.jperfman.chart.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class FileSaver {
	public static void saveData(Map data, String filePath) {
		File tmpFile = new File(filePath);
		BufferedWriter bw = null;
		FileOutputStream fos = null;
		OutputStreamWriter out = null;
		
		if (!tmpFile.exists()) {
			try {
				tmpFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fos = new FileOutputStream(tmpFile,true);
				out = new OutputStreamWriter(fos);
				bw = new BufferedWriter(out);
				
				for (Object key : data.keySet()) {
					String line = key.toString() + ":" + data.get(key).toString() + "\n";
					bw.write(line);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					bw.close();
					out.close();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

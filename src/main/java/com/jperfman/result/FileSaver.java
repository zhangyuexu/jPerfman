package com.jperfman.result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileSaver implements IResultSaver {

	private File file;
	
	public FileSaver(String fileDir, String fileName) {
		file = new File(fileDir + File.separator + fileName);
	}
	
	public void save(List<Result> list) {
		try {
			int cnt = 1;
			FileOutputStream out = new FileOutputStream(file, true);
			for (Result result : list) {
				StringBuffer sb = new StringBuffer();
				sb.append(cnt);
				sb.append(",");
				sb.append(result.toString());
				out.write(sb.toString().getBytes());
				cnt++;
			}
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

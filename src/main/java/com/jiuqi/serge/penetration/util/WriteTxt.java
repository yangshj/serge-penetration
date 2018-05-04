package com.jiuqi.serge.penetration.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteTxt {
	
	private static File out = null;
	private static FileWriter fw = null;
	private static BufferedWriter writer = null;
		
	/**
	 * 写文件，续写
	 * @param fileName 文件全路径
	 * @param content 写入内容 
	 */
	public synchronized static void write(String fileName, String content){
		out = new File(fileName);
		try {
			fw = new FileWriter(out, true);
			writer = new BufferedWriter(fw);
			writer.write(content);
			writer.newLine(); // 换行
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

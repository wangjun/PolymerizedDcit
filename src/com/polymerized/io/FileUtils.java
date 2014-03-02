package com.polymerized.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 与文件操作有关的工具类函数
 * 
 * @author edgar
 * 
 */
public class FileUtils {
	public boolean saveToFile(String content, String filename) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					filename)));
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

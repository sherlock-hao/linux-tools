package com.hao.linux.tool.kernel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileUtils {
	/**
	 * 读取文件内容
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String readFile(String path) {
		File file = new File(path);
		if (file.canRead()) {
			FileReader fileReader;
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			try {
				fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
					stringBuilder.append("\n");
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}

			return stringBuilder.toString();
		}

		throw new IllegalArgumentException(path);
	}
}

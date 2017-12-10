package com.hao.linux.tool.kernel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellUtils {
	public static String ecuteResult(String shell) {
		Process process = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			process = Runtime.getRuntime().exec(shell);
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
			input.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return stringBuilder.toString();
	}
}

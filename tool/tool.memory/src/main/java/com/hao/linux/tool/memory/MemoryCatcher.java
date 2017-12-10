package com.hao.linux.tool.memory;

import java.util.Scanner;

import com.hao.linux.tool.kernel.FileUtils;
import com.hao.linux.tool.memory.MemoryInfo.MemoryItemInfo;

public class MemoryCatcher {
	private static final String MEM_FILE = "/proc/meminfo";
	private MemoryInfo memoryInfo = new MemoryInfo();

	public void refresh() {
		memoryInfo.clear();
		String content = FileUtils.readFile(MEM_FILE);
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(content);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			MemoryItemInfo memoryItemInfo = parseMemoryItemInfo(line);
			memoryInfo.addItemInfo(memoryItemInfo);
		}

	}

	private MemoryItemInfo parseMemoryItemInfo(String line) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(line);
		String name = scanner.next();
		if (name.endsWith(":")) {
			name = name.substring(0, name.length() - 1);
		}
		long value = -1;
		String unit = null;
		if (scanner.hasNext()) {
			value = scanner.nextLong();
		}

		if (scanner.hasNext()) {
			unit = scanner.next();
		}

		MemoryItemInfo memoryItemInfo = new MemoryItemInfo(name, value, unit);
		return memoryItemInfo;
	}

	public Double getPercentage() {
		return memoryInfo.getUsedPercentage();
	}

	public String getFixedMemoryInfo() {
		return memoryInfo.getFixedMemoryInfo();
	}
}

package com.hao.linux.tool.memory;

/**
 * @author sherlock
 * 
 */
public class MemoryCalculator {
	MemoryCatcher memoryCatcher = new MemoryCatcher();

	public void update() {
		memoryCatcher.refresh();
	}

	public String getFixedInfo() {
		String percentageStr = memoryCatcher.getFixedMemoryInfo();
		while (percentageStr.length() < 5) {
			percentageStr = " " + percentageStr;
		}

		return  percentageStr;
	}
	
	public Double getPercentage() {
		return memoryCatcher.getPercentage();
	}
}

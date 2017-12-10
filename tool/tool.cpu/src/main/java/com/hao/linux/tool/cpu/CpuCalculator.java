package com.hao.linux.tool.cpu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.hao.linux.tool.kernel.NumberConstantsUtils;

public class CpuCalculator {
	private static final String CPU_FILE = "/proc/stat";
	double usedPercentage = 0;

	public String getFixedInfo() {
		String percentageStr = NumberConstantsUtils.format(usedPercentage, 1);
		while (percentageStr.length() < 4) {
			percentageStr = " " + percentageStr;
		}
		return percentageStr + "%";
	}

	public Double getUsedPercentage() {
		return usedPercentage;
	}

	public void update() {
		try {
			CPUTime startTime = getcpuTime();
			Thread.sleep(100);
			CPUTime endTime = getcpuTime();

			long totalTime = endTime.getTotalTime() - startTime.getTotalTime();
			if (totalTime == 0) {
				usedPercentage = 0;
			} else {
				usedPercentage = 100D
						- (((double) (endTime.getIdleTime() - startTime.getIdleTime())) / totalTime) * 100D;
			}

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static CPUTime getcpuTime() {
		CPUTime t = new CPUTime();
		BufferedReader fr = null;
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(new File(CPU_FILE));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("cpu ")) {
					String[] vals = line.substring(4).split(" ");
					if (vals.length < 10) {
						throw new RuntimeException((String.format("read an error cpu string : %s", line)));
					} else {
						t.setTotalTime(Long.parseLong(vals[1]) + Long.parseLong(vals[2]) + Long.parseLong(vals[3])
								+ Long.parseLong(vals[4]) + Long.parseLong(vals[5]) + Long.parseLong(vals[6])
								+ Long.parseLong(vals[7]) + Long.parseLong(vals[8]) + Long.parseLong(vals[9]));
						t.setIdleTime(Long.parseLong(vals[4]));
						break;
					}
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return t;
	}

	public static class CPUTime {
		private long totalTime;
		private long idleTime;

		public CPUTime() {
			totalTime = 0;
			idleTime = 0;
		}

		public long getTotalTime() {
			return totalTime;
		}

		public void setTotalTime(long totalTime) {
			this.totalTime = totalTime;
		}

		public long getIdleTime() {
			return idleTime;
		}

		public void setIdleTime(long idleTime) {
			this.idleTime = idleTime;
		}
	}

}

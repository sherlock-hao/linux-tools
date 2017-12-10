package com.hao.linux.tools.network;

/**
 * 网速计算器 统一单位 byte
 * 
 * @author sherlock
 *
 */
public class NetworkCalculator {
	private static final long K_VALUE = 1024;
	long updateTime = System.currentTimeMillis();
	NetworkCatcher networkCatcher = new NetworkCatcher();

	/**
	 * 上行速度 bytes/s
	 */
	double currentTXSpeed = 0L;

	/**
	 * 下行速度 bytes/s
	 */
	double currentRXSpeed = 0L;

	/**
	 * 上行流量
	 */
	long currentTX = 0L;

	/**
	 * 下行流量
	 */
	long currentRX = 0L;

	/**
	 * 网速更新
	 * 
	 * @param 当前上行流量
	 *            currentTX
	 * @param 当前下行流量
	 *            currentRX
	 */
	public void update() {
		networkCatcher.refresh();
		long currentTX = networkCatcher.getCurrentTX();
		long currentRX = networkCatcher.getCurrentRX();

		long now = System.currentTimeMillis();
		long span = now - updateTime;
		long spanTX = currentTX - this.currentTX;
		long spanRX = currentRX - this.currentRX;

		currentTXSpeed = spanTX * 1000 / span;
		currentRXSpeed = spanRX * 1000 / span;

		updateTime = now;
		this.currentTX = currentTX;
		this.currentRX = currentRX;
	}

	public double getTXSpeedInByte() {
		return currentTXSpeed;
	}

	public double getRXSpeedInByte() {
		return currentRXSpeed;
	}

	public double getTXSpeedInKB() {
		return getTXSpeedInByte() / K_VALUE;
	}

	public double getRXSpeedInKB() {
		return getRXSpeedInByte() / K_VALUE;
	}

	public double getTXSpeedInMB() {
		return getTXSpeedInKB() / K_VALUE;
	}

	public double getRXSpeedInMB() {
		return getRXSpeedInKB() / K_VALUE;
	}

	public double getTXSpeedInGB() {
		return getTXSpeedInMB() / K_VALUE;
	}

	public double getRXSpeedInGB() {
		return getRXSpeedInMB() / K_VALUE;
	}

	public String getFixedTXSpeed() {
		double byteValue = getTXSpeedInByte();
		String unit = "Byte";
		Double fixedValue = byteValue;
		if (fixedValue > K_VALUE) {
			fixedValue = getTXSpeedInKB();
			unit = "KB";
			if (fixedValue > K_VALUE) {
				fixedValue = getTXSpeedInMB();
				unit = "MB";
				if (fixedValue > K_VALUE) {
					fixedValue = getTXSpeedInGB();
					unit = "GB";
				}
			}
		}
		return fixedValue.intValue() + unit + "/s";
	}

	public String getFixedRXSpeed() {
		double byteValue = getRXSpeedInByte();
		String unit = "Byte";
		Double fixedValue = byteValue;
		if (fixedValue > K_VALUE) {
			fixedValue = getRXSpeedInKB();
			unit = "KB";
			if (fixedValue > K_VALUE) {
				fixedValue = getRXSpeedInMB();
				unit = "MB";
				if (fixedValue > K_VALUE) {
					fixedValue = getRXSpeedInGB();
					unit = "GB";
				}
			}
		}
		return fixedValue.intValue() + unit + "/s";
	}
}

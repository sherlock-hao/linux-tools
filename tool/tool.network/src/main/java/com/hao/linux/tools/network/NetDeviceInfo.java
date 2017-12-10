package com.hao.linux.tools.network;

import com.alibaba.fastjson.JSON;

public class NetDeviceInfo {
	private String name;
	private long infoTime;
	private long tx;
	private long rx;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public NetDeviceInfo(String name, long tx, long rx) {
		this.name = name;
		this.tx = tx;
		this.rx = rx;
		this.infoTime = System.currentTimeMillis();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(long infoTime) {
		this.infoTime = infoTime;
	}

	public long getTx() {
		return tx;
	}

	public void setTx(long tx) {
		this.tx = tx;
	}

	public long getRx() {
		return rx;
	}

	public void setRx(long rx) {
		this.rx = rx;
	}
}

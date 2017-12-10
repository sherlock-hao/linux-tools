package com.hao.linux.tools.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.hao.linux.tool.kernel.FileUtils;

public class NetworkCatcher {
	private static final String NET_DEV_FILE = "/proc/net/dev";
	private long currentTX = 0L;
	private long currentRX = 0L;
	private List<NetDeviceInfo> netDeviceList;

	/**
	 * rx的bytes数据在 /proc/net/dev 文件中出现的序号 从0开始
	 */
	private static int rxIndex = 0;

	/**
	 * tx的bytes数据在 /proc/net/dev 文件中出现的序号 从0开始
	 */
	private static int txIndex = 0;

	static {
		Scanner scanner = getScanner();

		// 此方法前提 /proc/net/dev 文件中，RX的信息先出现
		boolean rxFirst = true;
		int firstIndex = -1, secondIndex = -1;

		if (scanner.hasNextLine()) {
			scanner.nextLine();
			String line = scanner.nextLine();
			line = line.replace('|', ' ');
			Scanner lineScanner = new Scanner(line);
			int index = 0;
			while (lineScanner.hasNext()) {
				if ("bytes".equals(lineScanner.next())) {
					if (firstIndex == -1)
						firstIndex = index;
					else if (secondIndex == -1) {
						secondIndex = index;
					}
				}
				index++;
			}

			rxIndex = rxFirst ? firstIndex : secondIndex;
			txIndex = !rxFirst ? firstIndex : secondIndex;
		}
	}
	
	public NetworkCatcher() {
		//初始化先刷新网络数据,避免出现初始流量为0的情况
		refresh();
	}

	public void refresh() {
		netDeviceList = new ArrayList<NetDeviceInfo>();
		Scanner scanner = getScanner();
		scanner.nextLine();
		scanner.nextLine();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (StringUtils.isNotBlank(line)) {
				NetDeviceInfo netDeviceInfo = parseNetDeviceInfo(line);
				netDeviceList.add(netDeviceInfo);
			}
		}

		// TODO 暂时锁定第一个网卡来计算网速
		NetDeviceInfo firstNetDeviceInfo = netDeviceList.get(0);
		currentTX = firstNetDeviceInfo.getTx();
		currentRX = firstNetDeviceInfo.getRx();
	}

	private NetDeviceInfo parseNetDeviceInfo(String line) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(line);
		String name = scanner.next();
		long rx = -1, tx = -1;
		if (name.endsWith(":")) {
			name = name.substring(0, name.length() - 1);
		}
		int currentIndex = 1;
		while (scanner.hasNext()) {
			if (currentIndex == rxIndex) {
				rx = scanner.nextLong();
			} else if (currentIndex == txIndex) {
				tx = scanner.nextLong();
			} else {
				scanner.next();
			}
			currentIndex++;
		}

		NetDeviceInfo netDeviceInfo = new NetDeviceInfo(name, tx, rx);
		return netDeviceInfo;

	}

	static Scanner getScanner() {
		String content = FileUtils.readFile(NET_DEV_FILE);
		Scanner scanner = new Scanner(content);
		return scanner;
	}

	/**
	 * @return the currentTX
	 */
	public long getCurrentTX() {
		return currentTX;
	}

	/**
	 * @return the currentRX
	 */
	public long getCurrentRX() {
		return currentRX;
	}

}

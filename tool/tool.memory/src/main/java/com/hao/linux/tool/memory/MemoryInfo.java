package com.hao.linux.tool.memory;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hao.linux.tool.kernel.NumberConstantsUtils;

/**
 * 以byte 为单位
 * 
 * @author sherlock
 *
 */
public class MemoryInfo {
	Map<String, MemoryItemInfo> metaMap = new HashMap<String, MemoryInfo.MemoryItemInfo>();

	public void addItemInfo(MemoryItemInfo memoryItemInfo) {
		metaMap.put(memoryItemInfo.getName().toUpperCase(), memoryItemInfo);
	}

	public MemoryItemInfo getItemInfo(String name) {
		return metaMap.get(name.toUpperCase());
	}

	public void clear() {
		metaMap.clear();
	}

	/**
	 * 以byte 为单位
	 * 
	 * @author sherlock
	 *
	 */
	public static class MemoryItemInfo {
		public MemoryItemInfo(String name, long value, String unit) {
			this.name = name;
			if (StringUtils.isBlank(unit)) {
				unit = "B";
			}
			this.value = value * NumberConstantsUtils.getUnitValue(unit);
		}

		private String name;
		private long value;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the value
		 */
		public long getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		public void setValue(long value) {
			this.value = value;
		}

	}

	public String getFixedMemoryInfo() {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(1);
		Double percentage = getUsedPercentage();

		return numberFormat.format(percentage) + "%";
	}

	public Double getUsedPercentage() {
		Double available = (double) getAvailable();
		Double total = (double) getTotal();
		Double used = total - available;
		Double percentage = used * 100 / total;
		return percentage;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	/**
	 * @return the available
	 */
	public long getAvailable() {
		return getItemInfo("MemAvailable").getValue();
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return getItemInfo("MemTotal").getValue();
	}

}

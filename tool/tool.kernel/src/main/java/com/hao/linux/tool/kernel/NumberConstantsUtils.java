package com.hao.linux.tool.kernel;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class NumberConstantsUtils {
	public static final long K_VALUE = 1 << 10;
	private static final Map<Character, Long> UNIT_NUMBER_MAP = new HashMap<Character, Long>();
	static {
		UNIT_NUMBER_MAP.put('B', 1L);
		UNIT_NUMBER_MAP.put('K', 1L << 10);
		UNIT_NUMBER_MAP.put('M', 1L << 20);
		UNIT_NUMBER_MAP.put('G', 1L << 30);
		UNIT_NUMBER_MAP.put('T', 1L << 40);
	}

	/**
	 * 获取K,M,G,T单位的数量级
	 * 
	 * @param unit
	 * @return
	 */
	public static long getUnitValue(String unit) {
		unit = unit.trim();
		if (StringUtils.isBlank(unit)) {
			throw new RuntimeException("unit is empty");
		}
		Character firstChar = Character.toUpperCase(unit.charAt(0));
		Long value = UNIT_NUMBER_MAP.get(firstChar);
		return value != null ? value : 1L;
	}

	public static String format(double number, int digits) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(digits);
		return numberFormat.format(number);
	}
}

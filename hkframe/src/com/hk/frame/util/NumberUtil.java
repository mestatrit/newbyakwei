package com.hk.frame.util;

public class NumberUtil {

	public static Long getLong(Object obj) {
		Number n = getNumber(obj);
		if (n == null) {
			return 0L;
		}
		return n.longValue();
	}

	public static Number getNumber(Object obj) {
		if (obj == null) {
			return null;
		}
		return (Number) obj;
	}
}

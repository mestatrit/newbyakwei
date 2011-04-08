package com.hk.frame.util;

public class NumberUtil {

	public static long getLong(Object obj) {
		Number n = getNumber(obj);
		if (n == null) {
			return 0L;
		}
		return n.longValue();
	}

	public static int getInt(Object obj) {
		Number n = getNumber(obj);
		if (n == null) {
			return 0;
		}
		return n.intValue();
	}

	public static Number getNumber(Object obj) {
		if (obj == null) {
			return null;
		}
		return (Number) obj;
	}
}

package com.hk.web.util;

import java.util.Map;

public class BlackIpUtil {
	private static Map<String, Boolean> ipMap;

	public void setIpMap(Map<String, Boolean> ipMap) {
		BlackIpUtil.ipMap = ipMap;
	}

	public static boolean containIp(String ip) {
		return ipMap.containsKey(ip);
	}
}
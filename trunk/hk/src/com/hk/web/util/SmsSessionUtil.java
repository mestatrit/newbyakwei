package com.hk.web.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SmsSessionUtil {
	private static long distancetime = 20 * 60 * 1000;// 默认20分钟

	private static long sessionTime;

	private static final Map<String, SmsSession> sessionMap = new ConcurrentHashMap<String, SmsSession>();

	private SmsSessionUtil() {//
	}

	public void setSessionTime(long sessionTime) {
		SmsSessionUtil.sessionTime = sessionTime;
		distancetime = SmsSessionUtil.sessionTime * 60 * 1000;
	}

	public static SmsSession getSmsSession(String key) {
		SmsSession s = sessionMap.get(key);
		if (s == null) {
			s = new SmsSession(key);
			sessionMap.put(key, s);
		}
		s.updateActiveTime();
		return s;
	}

	public static int count() {
		return sessionMap.size();
	}

	public static void invoke() {
		SmsSessionUtil.clearOverTimeSmsSession();
	}

	private static void clearOverTimeSmsSession() {
		Collection<SmsSession> set = sessionMap.values();
		List<SmsSession> list = new ArrayList<SmsSession>();
		for (SmsSession o : set) {
			if (o.getActiveTime() + distancetime < System.currentTimeMillis()) {
				list.add(o);
			}
		}
		for (SmsSession o : list) {
			sessionMap.remove(o.getSessionId());
		}
	}
}
package com.hk.svr.pub;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	public static String getHkTime(Date date) {
		long v = System.currentTimeMillis() - date.getTime();
		long sec = v / 1000;
		long min = sec / 60;
		long h = min / 60;
		if (sec == 0) {
			return "1秒前";
		}
		StringBuilder sb = new StringBuilder();
		if (h > 0) {
			if (h < 24) {
				sb.append(h).append("小时前");
				return sb.toString();
			}
			long day = h / 24;
			if (day < 30 && day > 0) {
				sb.append(day).append("天前");
				return sb.toString();
			}
			long month = day / 30;
			if (month < 12 && month > 0) {
				sb.append(month).append("个月前");
				return sb.toString();
			}
			sb.append(month / 12).append("年前");
			return sb.toString();
		}
		if (min > 0) {
			sb.append(min).append("分前");
			return sb.toString();
		}
		sb.append(sec).append("秒前");
		return sb.toString();
	}

	public static String getWeekValue(Date createTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(createTime);
		int w = cal.get(Calendar.DAY_OF_WEEK);
		if (w == 1) {
			return "周日";
		}
		if (w == 2) {
			return "周一";
		}
		if (w == 3) {
			return "周二";
		}
		if (w == 4) {
			return "周三";
		}
		if (w == 5) {
			return "周四";
		}
		if (w == 6) {
			return "周五";
		}
		if (w == 7) {
			return "周六";
		}
		return null;
	}
}
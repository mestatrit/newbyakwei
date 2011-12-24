package com.hk.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PubDate {
	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	public static void main(String[] args) {
		System.out.println(PubDate.getNowTime());
	}

	public static String getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String week = convert(cal.get(Calendar.DAY_OF_WEEK));
		return week;
	}

	public static String getNowTime() {
		Calendar cal = Calendar.getInstance();
		String week = convert(cal.get(Calendar.DAY_OF_WEEK));
		String time = PubDate.sdf.format(cal.getTime());
		return new StringBuilder(time).append(" ").append(week).toString();
	}

	public static String convert(int val) {
		String retStr = "";
		switch (val - 1) {
		case 0:
			return "星期日";
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		default:
			break;
		}
		return retStr;
	}
}
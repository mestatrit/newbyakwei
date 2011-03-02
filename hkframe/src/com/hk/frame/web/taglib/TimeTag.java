package com.hk.frame.web.taglib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.jsp.JspWriter;

import com.hk.frame.util.P;

/**
 * 时间格式化标签
 * 
 * @author yuanwei
 */
public class TimeTag extends BaseBodyTag {
	private static final long serialVersionUID = 9091326980650870174L;

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

	private Date value;

	private boolean onlytoday;// 如果只特殊处理当天

	public void setOnlytoday(boolean onlytoday) {
		this.onlytoday = onlytoday;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		if (onlytoday) {// 显示为xx前
			Calendar now = Calendar.getInstance();
			Calendar date_Calendar = Calendar.getInstance();
			date_Calendar.setTime(value);
			int n_year = now.get(Calendar.YEAR);
			int n_month = now.get(Calendar.MONTH);
			int n_date = now.get(Calendar.DATE);
			int d_year = date_Calendar.get(Calendar.YEAR);
			int d_month = date_Calendar.get(Calendar.MONTH);
			int d_date = date_Calendar.get(Calendar.DATE);
			if (n_year == d_year && n_month == d_month && n_date == d_date) {
				writer.append(getTime(value));
			}
			else {
				writer.append(sdf.format(value));
			}
		}
		else {
			writer.append(getTime(value));
		}
	}

	public static String getTime(Date date) {
		long v = System.currentTimeMillis() - date.getTime();
		long sec = v / 1000;
		long min = sec / 60;
		long h = min / 60;
		if (sec == 0) {
			return "刚刚";
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
		// sb.append(sec).append("秒前");
		sb.append("刚刚");
		return sb.toString();
	}
	public static void main(String[] args) {
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -2);
		calendar.add(Calendar.MINUTE, -2);
		P.println(getTime(calendar.getTime()));
	}
}
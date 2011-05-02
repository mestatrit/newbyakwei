package cactus.web.taglib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.servlet.jsp.JspWriter;

public class FormatTimeTag extends BaseBodyTag {
	private static final long serialVersionUID = -8234255953771031311L;

	private String pattern;

	private Date value;

	private boolean showweek;

	private boolean timeline;

	private SimpleDateFormat sdf = new SimpleDateFormat();

	private SimpleDateFormat ensdf = new SimpleDateFormat("d'th' MMMM yyyy",
			Locale.US);

	public void setTimeline(boolean timeline) {
		this.timeline = timeline;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	public void setShowweek(boolean showweek) {
		this.showweek = showweek;
	}

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		StringBuilder sb = new StringBuilder();
		if (timeline) {
			sb.append(ensdf.format(value));
		}
		else {
			sdf.applyPattern(pattern);
			sb.append(sdf.format(value));
			if (showweek) {
				sb.append(" ").append(getWeek(value));
			}
		}
		writer.append(sb.toString());
	}

	public String getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String week = convert(cal.get(Calendar.DAY_OF_WEEK));
		return week;
	}

	public String convert(int val) {
		String retStr = "";
		switch (val - 1) {
		case 0:
			return "Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
		default:
			break;
		}
		return retStr;
	}
	// public String convert(int val) {
	// String retStr = "";
	// switch (val - 1) {
	// case 0:
	// return "星期日";
	// case 1:
	// return "星期一";
	// case 2:
	// return "星期二";
	// case 3:
	// return "星期三";
	// case 4:
	// return "星期四";
	// case 5:
	// return "星期五";
	// case 6:
	// return "星期六";
	// default:
	// break;
	// }
	// return retStr;
	// }
}
package com.hk.frame.web.taglib;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.jsp.JspWriter;

public class TimeLineTag extends BaseBodyTag {
	private static final long serialVersionUID = -2885242261235234672L;

	private final String HK_REQUEST_TIME = "hk_request_time";

	private final String HK_REQUEST_TIME_SHOW = "hk_request_time_show";

	private Date date;

	private boolean group;

	@Override
	protected void adapter(JspWriter writer) throws IOException {
		if (group) {// 时间合并显示
			Date old = (Date) this.getRequest().getAttribute(HK_REQUEST_TIME);
			if (old == null) {// 如果没有显示过时间，就显示新时间
				this.getRequest().setAttribute(HK_REQUEST_TIME, date);
				this.getRequest().setAttribute(HK_REQUEST_TIME_SHOW, true);
			}
			else {// 如果旧时间与新时间不一样，就显示新时间，并替换旧时间为当前显示时间
				Calendar old_Calendar = Calendar.getInstance();
				old_Calendar.setTime(old);
				Calendar date_Calendar = Calendar.getInstance();
				date_Calendar.setTime(date);
				if (old_Calendar.get(Calendar.YEAR) != date_Calendar
						.get(Calendar.YEAR)
						|| old_Calendar.get(Calendar.MONTH) != date_Calendar
								.get(Calendar.MONTH)
						|| old_Calendar.get(Calendar.DATE) != date_Calendar
								.get(Calendar.DATE)) {
					this.getRequest().setAttribute(HK_REQUEST_TIME, date);
					this.getRequest().setAttribute(HK_REQUEST_TIME_SHOW, true);
				}
				else {// 如果旧时间与新时间一样，则设置不再次显示时间
					this.getRequest().setAttribute(HK_REQUEST_TIME_SHOW, false);
				}
			}
		}
		else {
			this.getRequest().setAttribute(HK_REQUEST_TIME, date);
			this.getRequest().setAttribute(HK_REQUEST_TIME_SHOW, true);
		}
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}
}
package com.hk.web.company.action;

import java.util.Calendar;
import java.util.Date;

import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;

public class DateInfo {
	private Date beginTime;

	private Date endTime;

	public DateInfo(HkRequest req) {
		String dateflg = req.getStringAndSetAttr("dateflg");
		if ("today".equals(dateflg)) {// 查看当天的数据
			beginTime = DataUtil.getDate(new Date());
			endTime = DataUtil.getEndDate(new Date());
			String begin_date = DataUtil.getFormatTimeData(beginTime,
					"yyyy-MM-dd");
			String end_date = DataUtil.getFormatTimeData(endTime, "yyyy-MM-dd");
			req.setAttribute("begin_date", begin_date);
			req.setAttribute("end_date", end_date);
		}
		else if ("week".equals(dateflg)) {// 查看一个星期的数据
			Calendar cal = Calendar.getInstance();
			cal.setTime(DataUtil.getDate(new Date()));
			cal.set(Calendar.DAY_OF_WEEK, 1);
			beginTime = cal.getTime();
			cal.set(Calendar.DAY_OF_WEEK, 7);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			endTime = cal.getTime();
			String begin_date = DataUtil.getFormatTimeData(beginTime,
					"yyyy-MM-dd");
			String end_date = DataUtil.getFormatTimeData(endTime, "yyyy-MM-dd");
			req.setAttribute("begin_date", begin_date);
			req.setAttribute("end_date", end_date);
		}
		else if ("days7".equals(dateflg)) {// 查看7天之内的数据
			Calendar cal = Calendar.getInstance();
			endTime = cal.getTime();
			cal.add(Calendar.DATE, -7);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			beginTime = cal.getTime();
			String begin_date = DataUtil.getFormatTimeData(beginTime,
					"yyyy-MM-dd");
			String end_date = DataUtil.getFormatTimeData(endTime, "yyyy-MM-dd");
			req.setAttribute("begin_date", begin_date);
			req.setAttribute("end_date", end_date);
		}
		else if ("month".equals(dateflg)) {// 查看7天之内的数据
			Calendar cal = Calendar.getInstance();
			endTime = cal.getTime();
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			beginTime = cal.getTime();
			String begin_date = DataUtil.getFormatTimeData(beginTime,
					"yyyy-MM-dd");
			String end_date = DataUtil.getFormatTimeData(endTime, "yyyy-MM-dd");
			req.setAttribute("begin_date", begin_date);
			req.setAttribute("end_date", end_date);
		}
		else {
			String begin_date = req.getStringAndSetAttr("begin_date");
			String end_date = req.getStringAndSetAttr("end_date");
			if (begin_date != null && end_date != null) {
				this.beginTime = DataUtil.parseTime(begin_date, "yyyy-MM-dd");
				this.beginTime = DataUtil.getDate(this.beginTime);
				this.endTime = DataUtil.parseTime(end_date, "yyyy-MM-dd");
				this.endTime = DataUtil.getEndDate(this.endTime);
			}
			else {
				beginTime = DataUtil.getDate(new Date());
				endTime = DataUtil.getEndDate(new Date());
			}
		}
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
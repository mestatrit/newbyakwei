package com.hk.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import com.hk.svr.pub.ZoneUtil;

public class HkOrder {
	public static final byte STOPFLG_N = 0;// 未停止

	public static final byte STOPFLG_Y = 1;

	protected long hkObjId;

	protected int cityId;

	protected int money;// 竞价

	protected int stopflg;// 0:运行1:停止

	protected Date utime;// 最后更新时间

	protected int pday;// 持续时间，单位：天

	protected HkObj hkObj;

	protected long userId;

	public Pcity getPcity() {
		return ZoneUtil.getPcity(cityId);
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public int getJingJia() {
		BigDecimal o = new BigDecimal(this.money * 1.1).setScale(0,
				BigDecimal.ROUND_HALF_UP);
		return o.intValue();
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public HkObj getHkObj() {
		return hkObj;
	}

	public void setHkObj(HkObj hkObj) {
		this.hkObj = hkObj;
	}

	public long getHkObjId() {
		return hkObjId;
	}

	public void setHkObjId(long hkObjId) {
		this.hkObjId = hkObjId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStopflg() {
		return stopflg;
	}

	public void setStopflg(int stopflg) {
		this.stopflg = stopflg;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public int getPday() {
		return pday;
	}

	public void setPday(int pday) {
		this.pday = pday;
	}

	public Date getOverTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, this.getPday());
		return cal.getTime();
	}

	public int getResultMoney() {
		return this.pday * this.money;
	}

	public boolean isStop() {
		if (this.stopflg == STOPFLG_Y) {
			return true;
		}
		return false;
	}

	public boolean isCanUpdate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, this.getPday());
		Calendar now = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(this.utime);
		if (cal2.get(Calendar.YEAR) == now.get(Calendar.YEAR)
				&& cal2.get(Calendar.MONTH) == now.get(Calendar.MONTH)
				&& cal2.get(Calendar.DATE) == now.get(Calendar.DATE)) {// 如果当天已经更新过，就不能再次更新
			return false;
		}
		return true;
	}
}

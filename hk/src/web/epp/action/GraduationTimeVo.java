package web.epp.action;

import java.util.Date;

public class GraduationTimeVo {

	/**
	 * 时间开始刻度
	 */
	private Date beginTime;

	/**
	 * 时间结束刻度
	 */
	private Date endTime;

	/**
	 * 刻度间隔时间，单位为分钟
	 */
	private int amount;

	/**
	 * 是否可以预约
	 */
	private boolean canReserve;

	/**
	 * 是否是当前用户已经预约过的时间范围之内
	 */
	private boolean currentUser;

	public void makeEndTime() {
		this.endTime = new Date(this.beginTime.getTime() + amount * 60 * 1000);
	}

	public boolean isCanReserve() {
		return canReserve;
	}

	public void setCanReserve(boolean canReserve) {
		this.canReserve = canReserve;
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * 结束刻度时间是否在当前时间之前
	 * 
	 * @return
	 *         2010-7-29
	 */
	public boolean isExceedCurrentTime() {
		if (this.endTime.getTime() <= System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public void setCurrentUser(boolean currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isCurrentUser() {
		return currentUser;
	}
}
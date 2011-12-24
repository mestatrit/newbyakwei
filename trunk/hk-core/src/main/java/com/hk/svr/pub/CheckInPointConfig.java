package com.hk.svr.pub;

public class CheckInPointConfig {

	/**
	 * 用户注册获得的点数
	 */
	public static int userReg;

	/**
	 * 初次报到
	 */
	private static int firstCheckIn;

	/**
	 * 某日第一次报到
	 */
	private static int dateFirstCheckIn;

	/**
	 * 某日第二次报到
	 */
	private static int dateSecondCheckIn;

	/**
	 * 某日第三次报到
	 */
	private static int otherCheckIn;

	/**
	 * 足迹创建
	 */
	private static int companyCreate;

	/**
	 * 开1箱子的点数
	 */
	private static int openBoxPoints;

	public void setUserReg(int userReg) {
		CheckInPointConfig.userReg = userReg;
	}

	public static int getUserReg() {
		return userReg;
	}

	public void setOpenBoxPoints(int openBoxPoints) {
		CheckInPointConfig.openBoxPoints = openBoxPoints;
	}

	public static int getOpenBoxPoints() {
		return openBoxPoints;
	}

	public void setOtherCheckIn(int otherCheckIn) {
		CheckInPointConfig.otherCheckIn = otherCheckIn;
	}

	public static int getOtherCheckIn() {
		return otherCheckIn;
	}

	public void setCompanyCreate(int companyCreate) {
		CheckInPointConfig.companyCreate = companyCreate;
	}

	public static int getCompanyCreate() {
		return companyCreate;
	}

	public static int getFirstCheckIn() {
		return firstCheckIn;
	}

	public void setFirstCheckIn(int firstCheckIn) {
		CheckInPointConfig.firstCheckIn = firstCheckIn;
	}

	public static int getDateFirstCheckIn() {
		return dateFirstCheckIn;
	}

	public void setDateFirstCheckIn(int dateFirstCheckIn) {
		CheckInPointConfig.dateFirstCheckIn = dateFirstCheckIn;
	}

	public static int getDateSecondCheckIn() {
		return dateSecondCheckIn;
	}

	public void setDateSecondCheckIn(int dateSecondCheckIn) {
		CheckInPointConfig.dateSecondCheckIn = dateSecondCheckIn;
	}

	/**
	 * 根据报到条件获得点数
	 * 
	 * @param totalEffectCheckInCount 总共有效报到次数
	 * @param todayEffectCheckInCount 当天有效报到次数
	 * @return
	 *         2010-4-29
	 */
	public static int getPoints(int totalEffectCheckInCount,
			int todayEffectCheckInCount) {
		if (totalEffectCheckInCount == 0) {
			// 初次报到
			return CheckInPointConfig.getFirstCheckIn();
		}
		if (todayEffectCheckInCount == 0) {
			// 第一次报到
			return CheckInPointConfig.getDateFirstCheckIn();
		}
		if (todayEffectCheckInCount == 1) {
			// 第二次报到
			return CheckInPointConfig.getDateSecondCheckIn();
		}
		return 0;
	}
}
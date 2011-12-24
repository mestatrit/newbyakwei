package com.hk.svr.pub;

public class HkbConfig implements HkLog {

	private static int rmbChangeHkb = 100;// 人民币兑换酷币 1元:100个

	private static int openinfo;

	private static int invite;

	private static int createSmsLaba;

	private static int authCompany;

	private static int sendSms;// 发送短信

	private static int createSmsCompanyReview;

	/**
	 * 浏览火酷广告，每次消耗
	 */
	private static int viewHkAd = 1;

	/**
	 * 优惠券下载1次的消耗
	 */
	private static int couponDcount = 1;

	public static void setViewHkAd(int viewHkAd) {
		HkbConfig.viewHkAd = viewHkAd;
	}

	public static int getViewHkAd() {
		return viewHkAd;
	}

	public void setCouponDcount(int couponDcount) {
		HkbConfig.couponDcount = couponDcount;
	}

	public static int getCouponDcount() {
		return couponDcount;
	}

	public static double getRmbFromHkb(int hkb) {
		return (double) hkb / (double) 100;
	}

	public void setRmbChangeHkb(int rmbChangeHkb) {
		HkbConfig.rmbChangeHkb = rmbChangeHkb;
	}

	public static int getHkbFromRmb(int rmb) {
		return HkbConfig.rmbChangeHkb * rmb;
	}

	public static int getCreateSmsCompanyReview() {
		return createSmsCompanyReview;
	}

	public void setCreateSmsCompanyReview(int createSmsCompanyReview) {
		HkbConfig.createSmsCompanyReview = createSmsCompanyReview;
	}

	public void setSendSms(int sendSms) {
		HkbConfig.sendSms = sendSms;
	}

	public static int getSendSms() {
		return sendSms;
	}

	public void setCreateSmsLaba(int createSmsLaba) {
		HkbConfig.createSmsLaba = createSmsLaba;
	}

	public static int getCreateSmsLaba() {
		return createSmsLaba;
	}

	public void setAuthCompany(int authCompany) {
		HkbConfig.authCompany = authCompany;
	}

	public static int getAuthCompany() {
		return authCompany;
	}

	public void setInvite(int invite) {
		HkbConfig.invite = invite;
	}

	public static int getInvite() {
		return invite;
	}

	public void setOpeninfo(int openinfo) {
		HkbConfig.openinfo = openinfo;
	}

	public static int getOpeninfo() {
		return openinfo;
	}
}
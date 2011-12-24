package com.hk.svr.pub;

public interface HkLog {

	int CREATESMSLABA = 1;

	int INVITE = 2;

	int SYSPRESENT = 3;

	int MONEYBUG = 4;

	int CREATEBOX = 5;

	int DELBOX = 6;

	int DELBOXPRIZE = 7;

	int AUTHCOMPANY = 8;

	int CREATEINFORMATION = 9;

	int REG = 10;

	int FIRST_SET_HEAD = 11;

	int FIRST_SET_NICKNAME = 12;

	int FIRST_SET_SEX = 13;

	int FIRST_SET_INTRO = 14;

	int CREATELABA = 15;// 发喇叭

	int ADD_TAG_WHEN_CREATE_LABA = 16;

	int DELETELABA = 17;// 炸弹炸掉的喇叭

	int REREMOVELABA = 18;// 回复被炸掉的喇叭

	int CLICKTAGINLABA = 19;

	int SEND_SMS = 20;

	int SEND_LABA_SMS = 21;

	int DELETELABA_FORUSER = 22;// 普通删除

	int REREMOVELABA_FORUSER = 23;// 用户自己恢复喇叭

	int AWARD_CREATECOMPANY = 24;// 用户创建的足迹被认领，奖励

	int SEND_GROUP_ACT_SMS = 25;// 活动群发短信

	int REPLYLABA = 26;// 回应喇叭

	int REFLABA = 27;// 转载喇叭

	int VALIDATEEMAIL = 28;// 认证email

	int CANCELVALIDATEEMAIL = 29;// 取消认证email

	int CREATESMSCOMPANYREVIEW = 30;// 短信点评

	int SEND_SMS_TO_ME = 31;// 给自己发送短信

	int CMP_SEND_COUPON_SMS_TO_USER = 32;// 企业发送短信给用户

	int CMP_ADD_HKB = 33;// 企业酷币充值

	int CMP_ORDER_HKB = 34;// 企业竞排扣除酷币

	/**
	 * 发布优惠券
	 */
	int CREATECOUPON = 35;

	/**
	 * 发布广告
	 */
	int CREATEHKAD = 36;

	/**
	 * 删除广告，退回剩余
	 */
	int HKAD_REMAIN = 37;
}

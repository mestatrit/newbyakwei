package com.hk.bean;

import java.util.List;

public class Equipment {

	public static final byte funcflg_GOOD = 0;

	public static final byte funcflg_BAD = 1;

	private long eid;

	private String name;

	private String intro;

	/**
	 * 出现机率
	 */
	private double rate;

	/**
	 * 是否是副作用
	 */
	private byte funcflg;

	/**
	 * 0,普通卡,1可对抗卡
	 */
	private byte vsflg;

	/**
	 * 对人/足迹使用的标识,0:人,1:足迹
	 */
	private byte goalflg;

	public static final byte GOALFLG_USER = 0;

	public static final byte GOALFLG_CMP = 1;

	/**
	 * 驱动方式,0:自动使用,1:用户主动使用
	 */
	private byte drivenflg;

	public static final byte DRIVENFLG_AUTO = 0;

	public static final byte DRIVENFLG_USER = 1;

	public byte getDrivenflg() {
		return drivenflg;
	}

	public void setDrivenflg(byte drivenflg) {
		this.drivenflg = drivenflg;
	}

	/**
	 *副作用道具相对应的治理道具
	 */
	private List<Long> opponentList;

	public byte getGoalflg() {
		return goalflg;
	}

	public void setGoalflg(byte goalflg) {
		this.goalflg = goalflg;
	}

	public List<Long> getOpponentList() {
		return opponentList;
	}

	public void setVsflg(byte vsflg) {
		this.vsflg = vsflg;
	}

	public byte getVsflg() {
		return vsflg;
	}

	public boolean isVsUse() {
		if (this.vsflg == 1) {
			return true;
		}
		return false;
	}

	public void setOpponentList(List<Long> opponentList) {
		this.opponentList = opponentList;
	}

	public byte getFuncflg() {
		return funcflg;
	}

	public void setFuncflg(byte funcflg) {
		this.funcflg = funcflg;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getRate() {
		return rate;
	}

	public long getEid() {
		return eid;
	}

	public void setEid(long eid) {
		this.eid = eid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public boolean isForUser() {
		if (this.drivenflg == DRIVENFLG_USER && this.goalflg == GOALFLG_USER) {
			return true;
		}
		return false;
	}

	public boolean isForCmp() {
		if (this.drivenflg == DRIVENFLG_USER && this.goalflg == GOALFLG_CMP) {
			return true;
		}
		return false;
	}

	public void copy(Equipment equipment) {
		setEid(equipment.getEid());
		setDrivenflg(equipment.getDrivenflg());
		setFuncflg(equipment.getFuncflg());
		setGoalflg(equipment.getGoalflg());
		setIntro(equipment.getIntro());
		setName(equipment.getName());
		setRate(equipment.getRate());
		setVsflg(equipment.getVsflg());
		setOpponentList(equipment.getOpponentList());
	}
}
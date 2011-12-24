package com.hk.svr;

public class OpenBoxResult {

	private boolean noRemain;

	/**
	 * 宝箱终止
	 */
	private boolean boxStopped;

	/**
	 * 宝箱暂停
	 */
	private boolean boxPaused;

	/**
	 * 宝箱过期
	 */
	private boolean boxExpired;

	/**
	 * 超过开箱限制
	 */
	private boolean overOpenLimit;

	public boolean isNoRemain() {
		return noRemain;
	}

	public void setNoRemain(boolean noRemain) {
		this.noRemain = noRemain;
	}

	public boolean isBoxStopped() {
		return boxStopped;
	}

	public void setBoxStopped(boolean boxStopped) {
		this.boxStopped = boxStopped;
	}

	public boolean isBoxPaused() {
		return boxPaused;
	}

	public void setBoxPaused(boolean boxPaused) {
		this.boxPaused = boxPaused;
	}

	public boolean isBoxExpired() {
		return boxExpired;
	}

	public void setBoxExpired(boolean boxExpired) {
		this.boxExpired = boxExpired;
	}

	public boolean isOverOpenLimit() {
		return overOpenLimit;
	}

	public void setOverOpenLimit(boolean overOpenLimit) {
		this.overOpenLimit = overOpenLimit;
	}
}
package com.hk.bean;

public class PinkLabaDelInfo implements DelInfo {
	private long labaId;

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getLabaId() {
		return labaId;
	}

	public long getOptime() {
		return 0;
	}

	public long getOpuserId() {
		return 0;
	}

	public String getOtherParam() {
		return "labaId=" + labaId;
	}

	public int getType() {
		return PINK_LABA;
	}
}
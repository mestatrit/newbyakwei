package com.hk.bean;

public class LabaDelInfo implements DelInfo {
	private long labaId;

	private long opuserId;

	private long optime;

	private long labaUserId;

	public long getLabaUserId() {
		return labaUserId;
	}

	public void setLabaUserId(long labaUserId) {
		this.labaUserId = labaUserId;
	}

	public String getOtherParam() {
		return "labaId=" + labaId;
	}

	public int getType() {
		return DelInfo.LABA;
	}

	public long getOpuserId() {
		return opuserId;
	}

	public void setOpuserId(long opuserId) {
		this.opuserId = opuserId;
	}

	public long getOptime() {
		return optime;
	}

	public void setOptime(long optime) {
		this.optime = optime;
	}

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}
}
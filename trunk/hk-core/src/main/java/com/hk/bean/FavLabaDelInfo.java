package com.hk.bean;

public class FavLabaDelInfo implements DelInfo {
	private long labaId;

	private long optime;

	private long opuserId;

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getOptime() {
		return optime;
	}

	public void setOptime(long optime) {
		this.optime = optime;
	}

	public long getOpuserId() {
		return opuserId;
	}

	public void setOpuserId(long opuserId) {
		this.opuserId = opuserId;
	}

	public String getOtherParam() {
		return "labaId=" + labaId;
	}

	public int getType() {
		return FAV_LABA;
	}
}
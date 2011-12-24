package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "userbatchsms", id = "sysid")
public class UserBatchSms {
	private long sysId;

	private long msgId;

	private long receiverId;

	private byte smsstate;

	private String statemsg;

	public byte getSmsstate() {
		return smsstate;
	}

	public void setSmsstate(byte smsstate) {
		this.smsstate = smsstate;
	}

	public String getStatemsg() {
		return statemsg;
	}

	public void setStatemsg(String statemsg) {
		this.statemsg = statemsg;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}
}
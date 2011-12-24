package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "msgiddata", id = "sysid")
public class MsgIdData {
	private int sysId;

	private int msgId;

	public int getSysId() {
		return sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
}
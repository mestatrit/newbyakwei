package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;

@Table(name = "userfgtsms")
public class UserFgtSms {

	@SuppressWarnings("unused")
	private static char[] base = new char[] { 'k', '3', 'h', 'c', '0', 'p',
			'q', '6', '1', 'v', 'i', '5', 'a', 'u', 't', 'j', '4', 's', 'z',
			'd', 'o', 'w', '7', 'r', '2', 'l', 'g', 'b', 'm', 'f', 'y', '9',
			'n', '8', 'x', 'e' };

	@Id
	private long userId;

	@Column
	private String smscode;

	@Column
	private int sendCount;

	@Column
	private Date uptime;

	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	public int getSendCount() {
		return sendCount;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public Date getUptime() {
		return uptime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getSmscode() {
		return smscode;
	}

	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}

	public void addSendCount(int add) {
		this.sendCount = this.sendCount + add;
	}

	public void createSmscode() {
		// StringBuilder sb = new StringBuilder();
		// for (int i = 0; i < 4; i++) {
		// int v = DataUtil.getRandomNumber(36);
		// sb.append(base[v]);
		// }
		// this.smscode = sb.toString();
		this.smscode = DataUtil.getRandom(4);
	}
}
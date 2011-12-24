package com.hk.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "pvtchatmain", id = "mainId")
public class PvtChatMain {
	public static final byte READ_N = 0;

	public static final byte READ_Y = 1;

	private long mainId;

	private long userId;

	private long user2Id;

	private int noReadCount;

	/**
	 * 内容格式senderId>>msg>>createTime<>senderId>>msg>>createTime<>senderId>>msg>>createTime
	 */
	private String last3msg;

	private byte readflg;

	private Date createTime;

	private User user2;

	public long getTime() {
		return this.createTime.getTime();
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public User getUser2() {
		if (this.user2 == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			this.user2 = userService.getUser(user2Id);
		}
		return user2;
	}

	public long getMainId() {
		return mainId;
	}

	public void setMainId(long mainId) {
		this.mainId = mainId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUser2Id() {
		return user2Id;
	}

	public void setUser2Id(long user2Id) {
		this.user2Id = user2Id;
	}

	public String getLast3msg() {
		return last3msg;
	}

	public void setLast3msg(String last3msg) {
		this.last3msg = last3msg;
	}

	public byte getReadflg() {
		return readflg;
	}

	public void setReadflg(byte readflg) {
		this.readflg = readflg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isNewMsg() {
		if (this.readflg == READ_N) {
			return true;
		}
		return false;
	}

	public List<PvtChat> getMsgList() {
		List<PvtChat> list = new ArrayList<PvtChat>();
		if (this.last3msg != null && !"".equals(this.last3msg)) {
			String[] t = this.last3msg.split("<>");
			for (int i = 0; i < t.length; i++) {
				String[] tt = t[i].split(">>");
				PvtChat o = new PvtChat();
				o.setSenderId(Long.parseLong(tt[0]));
				o.setMsg(tt[1]);
				o.setCreateTime(new Date(Long.parseLong(tt[2])));
				list.add(o);
			}
		}
		return list;
	}

	public String getMsg() {
		if (this.last3msg != null && this.last3msg.length() != 0) {
			String[] t = this.last3msg.split("<>");
			String[] tt = t[t.length - 1].split(">>");
			return tt[1];
		}
		return null;
	}

	public int getNoReadCount() {
		return noReadCount;
	}

	public void setNoReadCount(int noReadCount) {
		this.noReadCount = noReadCount;
	}

	public static byte getREAD_N() {
		return READ_N;
	}

	public static byte getREAD_Y() {
		return READ_Y;
	}
}
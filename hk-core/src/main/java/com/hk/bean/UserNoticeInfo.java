package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "usernoticeinfo", id = "userid")
public class UserNoticeInfo {
	public static final byte NOTICE_Y = 0;

	public static final byte NOTICE_N = 1;

	private long userId;

	private byte msgNotice;// IM通知

	private byte followSysNotice;// 系统通知

	private byte followNotice;// 邮件通知

	private byte followIMNotice;// IM通知

	private byte labaReplySysNotice;// 系统通知

	private byte labaReplyNotice;// 邮件通知

	private byte labaReplyIMNotice;// IM通知

	private byte userInLabaSysNotice;// 喇叭中带有其他人的系统通知

	/**
	 * 是否进行喇叭中带有其他人的系统通知
	 * 
	 * @return
	 */
	public boolean isNoticeUserInLaba() {
		if (this.userInLabaSysNotice == NOTICE_Y) {
			return true;
		}
		return false;
	}

	public byte getUserInLabaSysNotice() {
		return userInLabaSysNotice;
	}

	public void setUserInLabaSysNotice(byte userInLabaSysNotice) {
		this.userInLabaSysNotice = userInLabaSysNotice;
	}

	public byte getFollowIMNotice() {
		return followIMNotice;
	}

	public void setFollowIMNotice(byte followIMNotice) {
		this.followIMNotice = followIMNotice;
	}

	public byte getLabaReplyIMNotice() {
		return labaReplyIMNotice;
	}

	public void setLabaReplyIMNotice(byte labaReplyIMNotice) {
		this.labaReplyIMNotice = labaReplyIMNotice;
	}

	public byte getLabaReplySysNotice() {
		return labaReplySysNotice;
	}

	public void setLabaReplySysNotice(byte labaReplySysNotice) {
		this.labaReplySysNotice = labaReplySysNotice;
	}

	public byte getFollowNotice() {
		return followNotice;
	}

	public void setFollowNotice(byte followNotice) {
		this.followNotice = followNotice;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getMsgNotice() {
		return msgNotice;
	}

	public void setMsgNotice(byte msgNotice) {
		this.msgNotice = msgNotice;
	}

	public byte getLabaReplyNotice() {
		return labaReplyNotice;
	}

	public void setLabaReplyNotice(byte labaReplyNotice) {
		this.labaReplyNotice = labaReplyNotice;
	}

	public boolean isNoticeMsg() {
		if (this.msgNotice == NOTICE_Y) {
			return true;
		}
		return false;
	}

	public boolean isNoticeLabaReplyForMail() {
		if (this.labaReplyNotice == NOTICE_Y) {
			return true;
		}
		return false;
	}

	public boolean isNoticeLabaReplyForIM() {
		if (this.labaReplyIMNotice == NOTICE_Y) {
			return true;
		}
		return false;
	}

	public boolean isNoticeFollowForMail() {
		if (this.followNotice == NOTICE_Y) {
			return true;
		}
		return false;
	}

	public boolean isNoticeFollowForIM() {
		if (this.followIMNotice == NOTICE_Y) {
			return true;
		}
		return false;
	}

	public boolean isSysNoticeLabaReply() {
		if (this.labaReplySysNotice == NOTICE_Y) {
			return true;
		}
		return false;
	}

	public boolean isSysNoticeFollow() {
		if (this.followSysNotice == NOTICE_Y) {
			return true;
		}
		return false;
	}

	public byte getFollowSysNotice() {
		return followSysNotice;
	}

	public void setFollowSysNotice(byte followSysNotice) {
		this.followSysNotice = followSysNotice;
	}
}
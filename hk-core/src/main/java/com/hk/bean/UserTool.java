package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "usertool", id = "userid")
public class UserTool {
	public static final byte SHOWREPLY_CHAR0 = 0;

	public static final byte SHOWREPLY_CHAR3 = 1;

	public static final byte SHOWREPLY_CHAR5 = 2;

	public static final byte SHOWREPLY_CHAR8 = 3;

	private long userId;

	private int groundCount;

	private byte labartflg;

	private int inviteCount;

	/**
	 * 0:列表中显示全部回应 1:不显示少于3个字符的回应 2:不显示少于5个字符的回应 3:不显示少于8个字符的回应
	 */
	private byte showReply;

	private User user;

	public static UserTool createDefault(long userId) {
		UserTool userTool = new UserTool();
		userTool.setUserId(userId);
		userTool.setGroundCount(3);
		userTool.setInviteCount(3);
		return userTool;
	}

	public static UserTool createEmpty() {
		UserTool userTool = new UserTool();
		return userTool;
	}

	private UserTool() {//
	}

	public byte getShowReply() {
		return showReply;
	}

	public void setShowReply(byte showReply) {
		this.showReply = showReply;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte getLabartflg() {
		return labartflg;
	}

	public void setLabartflg(byte labartflg) {
		this.labartflg = labartflg;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getGroundCount() {
		return groundCount;
	}

	public void setGroundCount(int groundCount) {
		this.groundCount = groundCount;
	}

	public void addGroundCount(int add) {
		// 数量不能超过20
		if (this.groundCount < 20) {
			this.groundCount = this.groundCount + add;
		}
		if (this.groundCount < 0) {
			this.groundCount = 0;
		}
	}

	public int validate() {
		if (this.labartflg < 0 || this.labartflg > 2) {
			return Err.USERTOOL_LABARTFLG_ERROR;
		}
		if (!DataUtil.isInElements(this.showReply, new Object[] {
				SHOWREPLY_CHAR0, SHOWREPLY_CHAR3, SHOWREPLY_CHAR5,
				SHOWREPLY_CHAR8 })) {
			return Err.USERTOOL_SHOWREPLY_ERROR;
		}
		return Err.SUCCESS;
	}

	public int getInviteCount() {
		return inviteCount;
	}

	public void setInviteCount(int inviteCount) {
		this.inviteCount = inviteCount;
	}
}
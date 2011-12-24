package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;

@Table(name = "invite", id = "inviteid")
public class Invite {
	public static final byte INVITETYPE_EMAIL = 0;

	public static final byte INVITETYPE_LINK = 1;

	public static final byte INVITETYPE_SMS = 2;

	public static final byte INVITETYPE_REGCODE = 3;

	public static final byte ADDHKBFLG_N = 0;

	public static final byte ADDHKBFLG_Y = 1;

	private long inviteId;

	private long userId;

	private long friendId;

	private String email;

	private Date createTime;

	private Date regTime;

	private int inviteCount;

	private Date uptime;

	private byte inviteType;

	private byte addhkbflg;// 0:未赠送 1:以赠送

	private User friend;

	public long getInviteId() {
		return inviteId;
	}

	public void setInviteId(long inviteId) {
		this.inviteId = inviteId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public User getFriend() {
		if (this.friend == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			this.friend = userService.getUser(this.friendId);
		}
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}

	public int getInviteCount() {
		return inviteCount;
	}

	public void setInviteCount(int inviteCount) {
		this.inviteCount = inviteCount;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public byte getInviteType() {
		return inviteType;
	}

	public void setInviteType(byte inviteType) {
		this.inviteType = inviteType;
	}

	public byte getAddhkbflg() {
		return addhkbflg;
	}

	public void setAddhkbflg(byte addhkbflg) {
		this.addhkbflg = addhkbflg;
	}
}
package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 或酷币充值
 * 
 * @author akwei
 */
@Table(name = "adminhkb", id = "sysid")
public class AdminHkb {

	public static final byte ADDFLG_MONEYBUY = 0;

	public static final byte ADDFLG_PRESENT = 1;

	private long sysId;

	private long userId;

	private long opuserId;// 操作人

	private int addCount;// 充值数量

	private int money;// 金钱数量

	private String content;// 备注信息

	private Date createTime;// 充值时间

	private byte addflg;// 充值方式

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setAddflg(byte addflg) {
		this.addflg = addflg;
	}

	public byte getAddflg() {
		return addflg;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOpuserId() {
		return opuserId;
	}

	public void setOpuserId(long opuserId) {
		this.opuserId = opuserId;
	}

	public int getAddCount() {
		return addCount;
	}

	public void setAddCount(int addCount) {
		this.addCount = addCount;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		if (this.userId == 0) {
			return Err.USERID_ERROR;
		}
		if (this.opuserId == 0) {
			return Err.USERID_ERROR;
		}
		if (this.addflg == ADDFLG_MONEYBUY) {
			if (money <= 0) {
				return Err.ADMINHKB_MONEY_ERROR;
			}
		}
		String s = DataUtil.toTextRow(this.content);
		if (!DataUtil.isEmpty(s)) {
			if (s.length() > 200) {
				return Err.ADMINHKB_CONTENT_LENGTH_TOO_LONG;
			}
		}
		return Err.SUCCESS;
	}
}
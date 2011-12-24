package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.BoxService;
import com.hk.svr.UserService;

@Table(name = "userboxprize", id = "sysid")
public class UserBoxPrize {

	/**
	 * 未领取
	 */
	public static final byte DRAWFLG_N = 0;

	/**
	 * 已领取
	 */
	public static final byte DRAWFLG_Y = 1;

	@Id
	private long sysId;

	@Column
	private long userId;

	@Column
	private long boxId;

	@Column
	private long prizeId;

	@Column
	private Date createTime;

	/**
	 * 序列号
	 */
	@Column
	private String prizeNum;

	/**
	 * 暗号
	 */
	@Column
	private String prizePwd;

	/**
	 * 是否已领取
	 */
	@Column
	private byte drawflg;

	@Column
	private Date drawTime;

	private BoxPrize boxPrize;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		if (user == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			user = userService.getUser(userId);
		}
		return user;
	}

	public BoxPrize getBoxPrize() {
		if (boxPrize == null) {
			BoxService boxService = (BoxService) HkUtil.getBean("boxService");
			boxPrize = boxService.getBoxPrize(prizeId);
		}
		return boxPrize;
	}

	public void setBoxPrize(BoxPrize boxPrize) {
		this.boxPrize = boxPrize;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public Date getDrawTime() {
		return drawTime;
	}

	public byte getDrawflg() {
		return drawflg;
	}

	public void setDrawflg(byte drawflg) {
		this.drawflg = drawflg;
	}

	public String getPrizeNum() {
		return prizeNum;
	}

	public void setPrizeNum(String prizeNum) {
		this.prizeNum = prizeNum;
	}

	public String getPrizePwd() {
		return prizePwd;
	}

	public void setPrizePwd(String prizePwd) {
		this.prizePwd = prizePwd;
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

	public long getBoxId() {
		return boxId;
	}

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}

	public long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(long prizeId) {
		this.prizeId = prizeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isDrawed() {
		if (this.drawflg == DRAWFLG_Y) {
			return true;
		}
		return false;
	}
}
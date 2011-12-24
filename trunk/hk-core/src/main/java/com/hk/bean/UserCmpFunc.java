package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户具有的企业功能，宝箱、优惠券、广告
 * 
 * @author akwei
 */
@Table(name = "usercmpfunc")
public class UserCmpFunc {

	/**
	 * 关闭
	 */
	public static final byte FLG_CLOSE = 0;

	/**
	 * 开启
	 */
	public static final byte FLG_OPEN = 1;

	@Id
	private long userId;

	/**
	 * 宝箱功能开启状态
	 */
	@Column
	private byte boxflg;

	/**
	 * 优惠券功能开启状态
	 */
	@Column
	private byte couponflg;

	/**
	 * 广告功能开启状态
	 */
	@Column
	private byte adflg;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getBoxflg() {
		return boxflg;
	}

	public void setBoxflg(byte boxflg) {
		this.boxflg = boxflg;
	}

	public byte getCouponflg() {
		return couponflg;
	}

	public void setCouponflg(byte couponflg) {
		this.couponflg = couponflg;
	}

	public byte getAdflg() {
		return adflg;
	}

	public void setAdflg(byte adflg) {
		this.adflg = adflg;
	}

	public boolean isBoxOpen() {
		if (this.boxflg == FLG_OPEN) {
			return true;
		}
		return false;
	}

	public boolean isCouponOpen() {
		if (this.couponflg == FLG_OPEN) {
			return true;
		}
		return false;
	}

	public boolean isHkAdOpen() {
		if (this.adflg == FLG_OPEN) {
			return true;
		}
		return false;
	}
}
package com.hk.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "usercoupon")
public class UserCoupon {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static final byte USERFLG_N = 0;

	public static final byte USERFLG_Y = 1;

	@Id
	private long oid;

	@Column
	private long userId;

	@Column
	private long couponId;

	@Column
	private Date createTime;

	@Column
	private String mcode;

	/**
	 * 使用状态
	 */
	@Column
	private byte useflg;

	private Coupon coupon;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getCreateTimeData() {
		return createTime.getTime();
	}

	/**
	 * 需要coupon属性,请先调用 setCoupon(Coupon coupon)
	 * 
	 * @return
	 */
	public String getEndTimeStr() {
		return sdf.format(UserCoupon.getEndTime(createTime, coupon));
	}

	public long getEndTimeData() {
		return this.getEndTime().getTime();
	}

	public Date getEndTime() {
		return UserCoupon.getEndTime(createTime, coupon);
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static Date getEndTime(Date createTime, Coupon coupon) {
		if (coupon.getOverdueflg() == Coupon.OVERDUEFLG_DAY) {
			Calendar c = Calendar.getInstance();
			c.setTime(createTime);
			c.add(Calendar.DATE, coupon.getLimitDay());
			return c.getTime();
		}
		return coupon.getEndTime();
	}

	public byte getUseflg() {
		return useflg;
	}

	public void setUseflg(byte useflg) {
		this.useflg = useflg;
	}

	public boolean isCouponUsed() {
		if (this.useflg == USERFLG_Y) {
			return true;
		}
		return false;
	}
}
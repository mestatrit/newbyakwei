package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;

@Table(name = "cmpproductuserstatus", id = "sysid")
public class CmpProductUserStatus {
	public static final byte USERSTATUS_WANT = 0;

	public static final byte USERSTATUS_DONE = 1;

	private long sysId;

	private long productId;

	private long userId;

	private byte userStatus;

	private long companyId;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(byte userStatus) {
		this.userStatus = userStatus;
	}

	public static boolean isStatus(byte s) {
		return DataUtil.isInElements(s, new Object[] { USERSTATUS_WANT,
				USERSTATUS_DONE });
	}

	public boolean isDone() {
		if (this.userStatus == USERSTATUS_DONE) {
			return true;
		}
		return false;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}
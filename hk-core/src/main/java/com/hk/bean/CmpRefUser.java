package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 当用户报到，或者在企业页面注册时，自动建立
 * 
 * @author akwei
 */
@Table(name = "cmprefuser")
public class CmpRefUser {

	public static final byte JOINFLG_REG = 0;

	public static final byte JOINFLG_LOGIN = 1;

	public static final byte JOINFLG_CHECKIN = 2;

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long userId;

	/**
	 * 加入方式
	 */
	@Column
	private byte joinflg;

	private User user;

	private UserOtherInfo userOtherInfo;

	public void setUserOtherInfo(UserOtherInfo userOtherInfo) {
		this.userOtherInfo = userOtherInfo;
	}

	public UserOtherInfo getUserOtherInfo() {
		return userOtherInfo;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte getJoinflg() {
		return joinflg;
	}

	public void setJoinflg(byte joinflg) {
		this.joinflg = joinflg;
	}
}
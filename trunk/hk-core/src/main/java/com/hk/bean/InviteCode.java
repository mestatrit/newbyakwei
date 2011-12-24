package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "invitecode")
public class InviteCode {

	public static final byte USEFLG_N = 0;

	public static final byte USEFLG_Y = 1;

	@Id
	private long oid;

	@Column
	private long userId;

	@Column
	private String data;

	@Column
	private byte useflg;

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public byte getUseflg() {
		return useflg;
	}

	public void setUseflg(byte useflg) {
		this.useflg = useflg;
	}

	public boolean isUnuse() {
		if (this.useflg == USEFLG_N) {
			return true;
		}
		return false;
	}
}
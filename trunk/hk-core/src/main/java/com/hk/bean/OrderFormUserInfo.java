package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "orderformuserinfo", id = "oid")
public class OrderFormUserInfo {
	public static final byte DEFFLG_MAIN = 1;

	@Id
	private long oid;

	@Column
	private String title;

	@Column
	private long userId;

	@Column
	private String name;

	@Column
	private String mobile;

	@Column
	private String tel;

	@Column
	private String email;

	/**
	 * 是否默认地址信息
	 */
	@Column
	private byte defflg;

	public byte getDefflg() {
		return defflg;
	}

	public void setDefflg(byte defflg) {
		this.defflg = defflg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int validate() {
		if (DataUtil.isEmpty(title)) {
			return Err.ORDERFORMUSERINFO_TITLE_ERROR;
		}
		if (DataUtil.toText(title).length() > 20) {
			return Err.ORDERFORMUSERINFO_TITLE_ERROR;
		}
		if (DataUtil.isEmpty(this.name)) {
			return Err.USER_NAME_ERROR;
		}
		if (DataUtil.toText(this.name).length() > 20) {
			return Err.USER_NAME_ERROR;
		}
		if (DataUtil.isEmpty(mobile)) {
			return Err.MOBILE_ERROR;
		}
		if (DataUtil.toText(this.mobile).length() > 20) {
			return Err.MOBILE_ERROR;
		}
		String s = DataUtil.toTextRow(tel);
		if (s != null && s.length() > 20) {
			return Err.TEL_ERROR;
		}
		s = DataUtil.toTextRow(email);
		if (s != null && s.length() > 50) {
			return Err.EMAIL_ERROR;
		}
		return Err.SUCCESS;
	}
}
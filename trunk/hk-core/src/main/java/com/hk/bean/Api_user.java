package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户所授权的第三方网站api信息
 * 
 * @author Administrator
 */
@Table(name = "api_user")
public class Api_user {

	/**
	 * 新浪授权
	 */
	public static final int API_TYPE_SINA = 1;

	/**
	 * 系统id
	 */
	@Id
	private long oid;

	/**
	 * 用户id
	 */
	@Column
	private long userid;

	/**
	 * 授权类型,参见 {@link Api_user#API_TYPE_SINA}
	 */
	@Column
	private int api_type;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public int getApi_type() {
		return api_type;
	}

	public void setApi_type(int apiType) {
		api_type = apiType;
	}
}
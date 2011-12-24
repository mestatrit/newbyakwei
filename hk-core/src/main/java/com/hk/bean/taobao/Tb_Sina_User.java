package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 新浪用户与火酷用户的对应表
 * 
 * @author akwei
 */
@Table(name = "tb_sina_user")
public class Tb_Sina_User {

	/**
	 * 新浪用户id
	 */
	@Id
	private long uid;

	@Column
	private long userid;

	private Tb_User tbUser;

	public Tb_User getTbUser() {
		return tbUser;
	}

	public void setTbUser(Tb_User tbUser) {
		this.tbUser = tbUser;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
}
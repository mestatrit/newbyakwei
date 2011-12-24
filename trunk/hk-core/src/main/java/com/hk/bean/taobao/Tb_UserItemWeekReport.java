package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_useritemweekreport")
public class Tb_UserItemWeekReport {

	@Id
	private long oid;

	@Column
	private long userid;

	@Column
	private int item_num;

	@Column
	private Date create_week;

	private Tb_User tbUser;

	public Tb_User getTbUser() {
		return tbUser;
	}

	public void setTbUser(Tb_User tbUser) {
		this.tbUser = tbUser;
	}

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

	public int getItem_num() {
		return item_num;
	}

	public void setItem_num(int itemNum) {
		item_num = itemNum;
	}

	public void setCreate_week(Date createWeek) {
		create_week = createWeek;
	}

	public Date getCreate_week() {
		return create_week;
	}
}
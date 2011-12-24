package com.hk.bean.taobao;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_useritemdailyreport")
public class Tb_UserItemDailyReport {

	@Id
	private long oid;

	@Column
	private long userid;

	@Column
	private int item_num;

	@Column
	private Date create_date;

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

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date createDate) {
		create_date = createDate;
	}
}
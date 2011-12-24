package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

/**
 * 关键词竞排价格的最低设置
 * 
 * @author akwei
 */
@Table(name = "hkobjkeytagorderdef", id = "oid")
public class HkObjKeyTagOrderDef {
	private long oid;

	private long tagId;

	private int cityId;

	private int money;

	public void setMoney(int money) {
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int validate() {
		if (this.cityId < 0) {
			return Err.ZONE_ERROR;
		}
		if (this.tagId < 1) {
			return Err.KEYTAGID_ERROR;
		}
		if (this.money < 1) {
			return Err.KEYTAG_HKB_ERROR;
		}
		return Err.SUCCESS;
	}
}
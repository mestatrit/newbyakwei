package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

/**
 * 竞排价格的最低设置, 最低竞拍价格可以根据分类,地区,级别进行设置
 * 
 * @author akwei
 */
@Table(name = "hkobjorderdef", id = "oid")
public class HkObjOrderDef {
	public static final byte KIND_HOMEPAGE = 0;// 首页

	public static final byte KIND_LEVEL_1 = 1;// 1级导航

	public static final byte KIND_LEVEL_2 = 2;// 2级导航

	private int oid;

	private byte kind;// 0:首页 1:一级导航 2:2级导航

	private int kindId;// 足迹 宝贝分类

	private int cityId;

	private int money;// 最低竞排价格

	public void setMoney(int money) {
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public byte getKind() {
		return kind;
	}

	public void setKind(byte kind) {
		this.kind = kind;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int validate() {
		if (this.kind != KIND_LEVEL_1 && this.kind != KIND_LEVEL_2) {
			return Err.HKOBJORDERDEF_KIND_ERROR;
		}
		if (this.kindId < 1) {
			return Err.COMPANYKINDID_ERROR;
		}
		if (this.money == 0) {
			return Err.HKOBJORDERDEF_HKB_ERROR;
		}
		if (this.cityId < 0) {
			return Err.ZONE_ERROR;
		}
		return Err.SUCCESS;
	}
}
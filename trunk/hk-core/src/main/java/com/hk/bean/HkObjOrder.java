package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

/**
 * 火酷宝贝、足迹排序类，根据宝贝足迹的充值情况已经竞拍价格按照一定顺序进行排列.出现排序的地方为 首页 1级导航 2级导航.
 * 
 * @author akwei
 */
@Table(name = "hkobjorder", id = "oid")
public class HkObjOrder extends HkOrder {
	private long oid;

	private byte kind;// 0:首页 1:1级导航 2:2级导航

	public boolean isShowIndex() {
		if (this.kind == HkObjOrderDef.KIND_LEVEL_1) {
			return true;
		}
		return false;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public byte getKind() {
		return kind;
	}

	public void setKind(byte kind) {
		this.kind = kind;
	}

	public int validate() {
		if (this.userId < 1) {
			return Err.USERID_ERROR;
		}
		if (this.money < 1) {
			return Err.HKOBJORDER_MONEY_ERROR;
		}
		if (this.pday < 1) {
			return Err.HKOBJORDER_PDAY_ERROR;
		}
		if (this.stopflg != STOPFLG_Y && this.stopflg != STOPFLG_N) {
			return Err.HKOBJORDER_STOPFLG_ERROR;
		}
		if (this.kind != HkObjOrderDef.KIND_HOMEPAGE
				&& this.kind != HkObjOrderDef.KIND_LEVEL_1
				&& this.kind != HkObjOrderDef.KIND_LEVEL_2) {
			return Err.HKOBJORDER_KIND_ERROR;
		}
		if (this.cityId < 0) {
			return Err.ZONE_ERROR;
		}
		return Err.SUCCESS;
	}
}
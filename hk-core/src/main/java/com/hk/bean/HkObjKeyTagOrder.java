package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.Err;

@Table(name = "hkobjkeytagorder", id = "oid")
public class HkObjKeyTagOrder extends HkOrder {
	private long oid;

	private long tagId;

	private KeyTag keyTag;

	public KeyTag getKeyTag() {
		return keyTag;
	}

	public void setKeyTag(KeyTag keyTag) {
		this.keyTag = keyTag;
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

	public void setStopflg(byte stopflg) {
		this.stopflg = stopflg;
	}

	public int validate() {
		if (this.userId < 1) {
			return Err.USERID_ERROR;
		}
		if (this.cityId < 0) {
			return Err.ZONE_ERROR;
		}
		if (this.stopflg != STOPFLG_N && this.stopflg != STOPFLG_Y) {
			return Err.HKOBJKEYTAGORDER_STOPFLG_ERROR;
		}
		if (this.pday < 1) {
			return Err.HKOBJKEYTAGORDER_PDAY_ERROR;
		}
		if (this.money < 1) {
			return Err.HKOBJKEYTAGORDER_MONEY_ERROR;
		}
		if (this.tagId < 1) {
			return Err.HKOBJKEYTAGORDER_TAGID_ERROR;
		}
		return Err.SUCCESS;
	}
}
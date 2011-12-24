package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 足迹的子分类
 * 
 * @author akwei
 */
@Table(name = "cmpchildkind", id = "oid")
public class CmpChildKind {
	private int oid;

	private int kindId;

	private String name;

	private int cmpCount;

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCmpCount() {
		return cmpCount;
	}

	public void setCmpCount(int cmpCount) {
		this.cmpCount = cmpCount;
	}

	public int validate() {
		if (this.kindId <= 0) {
			return Err.CMPCHILDKIND_KINDID_ERROR;
		}
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s)) {
			return Err.CMPCHILDKIND_NAME_ERROR;
		}
		if (s.length() > 20) {
			return Err.CMPCHILDKIND_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}
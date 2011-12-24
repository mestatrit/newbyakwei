package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "hkobj", id = "objid")
public class HkObj {
	public static final byte COMPANYSTATUS_CHECKFAIL = -1;// 审核不通过

	public static final byte COMPANYSTATUS_UNCHECK = 0;// 未审核

	public static final byte COMPANYSTATUS_CHECKED = 1;// 审核通过

	private long objId;

	private String name;

	private byte checkflg;

	private int kindId;

	public long getObjId() {
		return objId;
	}

	public void setObjId(long objId) {
		this.objId = objId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getCheckflg() {
		return checkflg;
	}

	public void setCheckflg(byte checkflg) {
		this.checkflg = checkflg;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}
}
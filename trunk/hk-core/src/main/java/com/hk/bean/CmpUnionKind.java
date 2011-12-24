package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpunionkind", id = "kindid")
public class CmpUnionKind {
	public static final byte HASCHILDFLG_N = 0;

	public static final byte HASCHILDFLG_Y = 1;

	@Id
	private long kindId;

	@Column
	private long uid;

	@Column
	private String name;

	@Column
	private long parentId;

	@Column
	private int kindLevel;

	@Column
	private byte hasChildflg;

	/**
	 * 显示顺序，asc
	 */
	@Column
	private int orderflg;

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public int getKindLevel() {
		return kindLevel;
	}

	public void setKindLevel(int kindLevel) {
		this.kindLevel = kindLevel;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public byte getHasChildflg() {
		return hasChildflg;
	}

	public void setHasChildflg(byte hasChildflg) {
		this.hasChildflg = hasChildflg;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public boolean isHasChild() {
		if (this.hasChildflg == HASCHILDFLG_Y) {
			return true;
		}
		return false;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPUNIONKIND_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}
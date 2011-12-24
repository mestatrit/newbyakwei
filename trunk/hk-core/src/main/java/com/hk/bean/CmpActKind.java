package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpactkind", id = "kindid")
public class CmpActKind {
	@Id
	private long kindId;

	@Column
	private String name;

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

	public int validate() {
		String s = DataUtil.toTextRow(name);
		if (DataUtil.isEmpty(s)) {
			return Err.CMPACTKIND_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}
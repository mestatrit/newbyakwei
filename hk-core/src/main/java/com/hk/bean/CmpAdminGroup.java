package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 火酷系统定义的足迹组
 * 
 * @author akwei
 */
@Table(name = "cmpadmingroup")
public class CmpAdminGroup {
	@Id
	private long groupId;

	@Column
	private String name;

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int validate() {
		String s = DataUtil.toTextRow(name);
		if (DataUtil.isEmpty(s) || s.length() > 30) {
			return Err.CMPADMINGROUP_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}
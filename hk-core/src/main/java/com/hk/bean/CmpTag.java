package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 用户自定义添加的标签
 * 
 * @author akwei
 */
@Table(name = "cmptag")
public class CmpTag {
	@Id
	private long tagId;

	@Column
	private String name;

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int validate() {
		String s = DataUtil.toTextRow(name);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPTAG_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}
package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 帖子id生成
 * 
 * @author akwei
 */
@Table(name = "cmpbbsiddata")
public class CmpBbsIdData {

	public CmpBbsIdData(Date createTime) {
		this.setCreateTime(createTime);
	}

	public CmpBbsIdData() {
	}

	@Id
	private long bbsId;

	@Column
	private Date createTime;

	public long getBbsId() {
		return bbsId;
	}

	public void setBbsId(long bbsId) {
		this.bbsId = bbsId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
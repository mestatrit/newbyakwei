package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 企业论坛内容对象
 * 
 * @author akwei
 */
@Table(name = "cmpbbscontent")
public class CmpBbsContent {

	public CmpBbsContent() {
	}

	public CmpBbsContent(CmpBbsDel cmpBbsDel) {
		this.setBbsId(cmpBbsDel.getBbsId());
		this.setContent(cmpBbsDel.getContent());
	}

	@Id
	private long bbsId;

	@Column
	private String content;

	public long getBbsId() {
		return bbsId;
	}

	public void setBbsId(long bbsId) {
		this.bbsId = bbsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.content);
		if (s == null || s.length() > 5000) {
			return Err.CMPBBSCONTENT_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}
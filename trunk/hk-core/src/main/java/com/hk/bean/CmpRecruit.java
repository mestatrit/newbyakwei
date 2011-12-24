package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmprecruit", id = "companyid")
public class CmpRecruit {
	private long companyId;

	private String title;

	private String content;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int validate() {
		if (this.companyId <= 0) {
			return Err.HKOBJID_ERROR;
		}
		String s = DataUtil.toTextRow(title);
		if (DataUtil.isEmpty(s)) {
			return Err.CMPRECRUIT_TITLE_ERROR;
		}
		if (s.length() > 20) {
			return Err.CMPRECRUIT_TITLE_LENGTH_TOO_LONG;
		}
		s = DataUtil.toText(this.content);
		if (DataUtil.isEmpty(s)) {
			return Err.CMPRECRUIT_CONTENT_ERROR;
		}
		if (s.length() > 1000) {
			return Err.CMPRECRUIT_CONTENT_LENGTH_TOO_LONG;
		}
		return Err.SUCCESS;
	}
}
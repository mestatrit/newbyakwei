package com.hk.bean;

import java.util.Date;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpbulletin", id = "bulletinid")
public class CmpBulletin {
	private int bulletinId;

	private long companyId;

	private String title;

	private String content;

	private Date createTime;

	public long getTime() {
		return this.createTime.getTime();
	}

	public int getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(int bulletinId) {
		this.bulletinId = bulletinId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		if (this.companyId <= 0) {
			return Err.HKOBJID_ERROR;
		}
		String t = DataUtil.toTextRow(this.content);
		if (DataUtil.isEmpty(t)) {
			return Err.CMPBULLETIN_CONTENT_ERROR;
		}
		if (t.length() > 500) {
			return Err.CMPBULLETIN_CONTENT_LENGTH_TOO_LONG;
		}
		t = DataUtil.toTextRow(this.title);
		if (DataUtil.isEmpty(t)) {
			return Err.CMPBULLETIN_TITLE_ERROR;
		}
		if (t.length() > 20) {
			return Err.CMPBULLETIN_TITLE_LENGTH_TOO_LONG;
		}
		return Err.SUCCESS;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
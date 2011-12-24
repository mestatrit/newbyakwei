package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpunionlink", id = "linkid")
public class CmpUnionLink {
	@Id
	private long linkId;

	@Column
	private long uid;

	@Column
	private String title;

	@Column
	private String url;

	public long getLinkId() {
		return linkId;
	}

	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int validate() {
		String s = DataUtil.toText(title);
		if (DataUtil.isEmpty(s) || s.length() > 30) {
			return Err.CMPUNIONLINK_TITLE_ERROR;
		}
		s = DataUtil.toText(url);
		if (DataUtil.isEmpty(s) || s.length() > 100) {
			return Err.CMPUNIONLINK_URL_ERROR;
		}
		return Err.SUCCESS;
	}
}
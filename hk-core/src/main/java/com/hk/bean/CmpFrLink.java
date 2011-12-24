package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 企业友情链接
 * 
 * @author akwei
 */
@Table(name = "cmpfrlink")
public class CmpFrLink {

	@Id
	private long linkId;

	@Column
	private long companyId;

	@Column
	private String name;

	@Column
	private String url;

	public long getLinkId() {
		return linkId;
	}

	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int validate() {
		String s = DataUtil.toText(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 50) {
			return Err.CMPFRLINK_NAME_ERROR;
		}
		s = DataUtil.toText(this.url);
		if (DataUtil.isEmpty(s) || s.length() > 200) {
			return Err.CMPFRLINK_URL_ERROR;
		}
		return Err.SUCCESS;
	}
}
package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业网站首页焦点图广告，适用于电子商务模板的网站
 * 
 * @author akwei
 */
@Table(name = "cmphomepicad")
public class CmpHomePicAd {

	@Id
	private long adid;

	@Column
	private long companyId;

	@Column
	private String path;

	@Column
	private String title;

	@Column
	private String url;

	public void setAdid(long adid) {
		this.adid = adid;
	}

	public long getAdid() {
		return adid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
		if (!HkValidate.validateEmptyAndLength(this.title, true, 20)) {
			return Err.CMPHOMEPICAD_TITLE_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.url, false, 200)) {
			return Err.CMPHOMEPICAD_URL_ERROR;
		}
		return Err.SUCCESS;
	}

	public String getPicUrl() {
		return ImageConfig.getCmpHomePicAdUrl(this.path);
	}
}
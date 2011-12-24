package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 企业上传的视频(视频包括上传到服务器上的，以及提供链接的,以flash格式为准)
 * 
 * @author akwei
 */
@Table(name = "cmpvideo")
public class CmpVideo {

	@Id
	private long oid;

	/**
	 * 文件名称
	 */
	@Column
	private String name;

	/**
	 * 提供的视频链接代码
	 */
	@Column
	private String html;

	/**
	 * 视频介绍
	 */
	@Column
	private String intro;

	/**
	 * 上传到服务器的文件
	 */
	@Column
	private String path;

	@Column
	private long companyId;

	@Column
	private long cmpNavOid;

	@Column
	private long fileSize;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getCmpNavOid() {
		return cmpNavOid;
	}

	public void setCmpNavOid(long cmpNavOid) {
		this.cmpNavOid = cmpNavOid;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 40)) {
			return Err.CMPVIDEO_NAME_ERROR;
		}
		if (!HkValidate.validateLength(this.html, false, 500)) {
			return Err.CMPVIDEO_HTML_ERROR;
		}
		if (!HkValidate.validateLength(this.intro, true, 500)) {
			return Err.CMPVIDEO_INTRO_ERROR;
		}
		return Err.SUCCESS;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
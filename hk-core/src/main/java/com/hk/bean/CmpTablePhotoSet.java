package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

@Table(name = "cmptablephotoset", id = "setid")
public class CmpTablePhotoSet {
	private long setId;

	private long companyId;

	private String title;

	private String intro;

	/**
	 * 头图路径
	 */
	private String path;

	public long getSetId() {
		return setId;
	}

	public void setSetId(long setId) {
		this.setId = setId;
	}

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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getPic60() {
		return ImageConfig.getPic60Url(path);
	}

	public String getPic240() {
		return ImageConfig.getPic240Url(path);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.title);
		if (DataUtil.isEmpty(s) || s.length() > 15) {
			return Err.CMPTABLEPHOTOSET_TITLE_ERROR;
		}
		s = DataUtil.toTextRow(this.intro);
		if (DataUtil.isEmpty(s) || s.length() > 15) {
			return Err.CMPTABLEPHOTOSET_INTRO_ERROR;
		}
		return Err.SUCCESS;
	}
}
package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业图片图集
 * 
 * @author akwei
 */
@Table(name = "cmpphotoset")
public class CmpPhotoSet {

	@Id
	private long setId;

	@Column
	private long companyId;

	@Column
	private String name;

	/**
	 * 头图路径
	 */
	@Column
	private String picPath;

	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPic60() {
		return ImageConfig.getPic60Url(this.picPath);
	}

	public String getPic320() {
		return ImageConfig.getPic320Url(picPath);
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPPHOTOSET_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}
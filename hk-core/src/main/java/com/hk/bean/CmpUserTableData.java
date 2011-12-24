package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 自定义数据数据表，可以自定义字段说明,以及显示方式(单行文本，多行文本，单选框，多选框，单选列表)
 * 
 * @author akwei
 */
@Table(name = "cmpusertabledata")
public class CmpUserTableData {

	@Id
	private long dataId;

	@Column
	private long cmpNavOid;

	/**
	 * 自定义提示信息
	 */
	@Column
	private String title;

	@Column
	private long companyId;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public int validate() {
		if (!HkValidate.validateLength(this.title, true, 200)) {
			return Err.CMPUSERTABLEDATA_TITLE_ERROR;
		}
		return Err.SUCCESS;
	}
}
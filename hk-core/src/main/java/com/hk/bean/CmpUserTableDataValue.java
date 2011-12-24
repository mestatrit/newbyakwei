package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户输入数据表，自定义的表的数据
 * 
 * @author akwei
 */
@Table(name = "cmpusertabledatavalue")
public class CmpUserTableDataValue {

	@Id
	private long vid;

	@Column
	private long companyId;

	@Column
	private long dataId;

	/**
	 * 输入框(文字数据)，单选框(选择项的id)，复选框(选择项id)，select选择框的值(选择项id)，最好使用大文本字段存储
	 * 里面数据包括fieldId,用户数据
	 */
	@Column
	private String data;

	@Column
	private Date create_time;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getVid() {
		return vid;
	}

	public void setVid(long vid) {
		this.vid = vid;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public long getDataId() {
		return dataId;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
}
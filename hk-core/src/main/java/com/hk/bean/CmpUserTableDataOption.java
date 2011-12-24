package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 选择框，复选框，单选框的用户自定义数据
 * 
 * @author akwei
 */
@Table(name = "cmpusertabledataoption")
public class CmpUserTableDataOption {

	@Id
	private long optionId;

	@Column
	private long companyId;

	@Column
	private long dataId;

	@Column
	private long fieldId;

	/**
	 * 选项数据
	 */
	@Column
	private String data;

	@Column
	private int orderflg;

	public long getOptionId() {
		return optionId;
	}

	public void setOptionId(long optionId) {
		this.optionId = optionId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public long getDataId() {
		return dataId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getFieldId() {
		return fieldId;
	}

	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.data, true, 20)) {
			return Err.CMPUSERTABLEDATAOPTION_DATA_ERROR;
		}
		return Err.SUCCESS;
	}
}
package com.hk.bean;

import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 自定义表中的字段,单行文本，多行文本，单选框，多选框，单选列表)
 * 
 * @author akwei
 */
@Table(name = "cmpusertablefield")
public class CmpUserTableField {

	public static final byte FIELD_TYPE_TEXT = 0;

	public static final byte FIELD_TYPE_TEXTAREA = 1;

	public static final byte FIELD_TYPE_SELECT = 2;

	public static final byte FIELD_TYPE_RADIO = 3;

	public static final byte FIELD_TYPE_CHECKBOX = 4;

	@Id
	private long fieldId;

	@Column
	private long dataId;

	@Column
	private long companyId;

	@Column
	private String name;

	@Column
	private byte field_type;

	/**
	 * 排序字段，数值越大，越靠前
	 */
	@Column
	private int orderflg;

	private List<CmpUserTableDataOption> optionList;

	private List<CmpUserTableDataOption> selectedOptionList;

	public void setSelectedOptionList(
			List<CmpUserTableDataOption> selectedOptionList) {
		this.selectedOptionList = selectedOptionList;
	}

	public List<CmpUserTableDataOption> getSelectedOptionList() {
		return selectedOptionList;
	}

	public long getFieldId() {
		return fieldId;
	}

	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
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

	public byte getField_type() {
		return field_type;
	}

	public void setField_type(byte fieldType) {
		field_type = fieldType;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPUSERTABLEFIELD_NAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public boolean isHasOption() {
		if (this.field_type == FIELD_TYPE_SELECT
				|| this.field_type == FIELD_TYPE_RADIO
				|| this.field_type == FIELD_TYPE_CHECKBOX) {
			return true;
		}
		return false;
	}

	public boolean isFieldText() {
		if (this.field_type == FIELD_TYPE_TEXT) {
			return true;
		}
		return false;
	}

	public boolean isFieldTextArea() {
		if (this.field_type == FIELD_TYPE_TEXTAREA) {
			return true;
		}
		return false;
	}

	public boolean isFieldSelect() {
		if (this.field_type == FIELD_TYPE_SELECT) {
			return true;
		}
		return false;
	}

	public boolean isFieldRadio() {
		if (this.field_type == FIELD_TYPE_RADIO) {
			return true;
		}
		return false;
	}

	public boolean isFieldCheckbox() {
		if (this.field_type == FIELD_TYPE_CHECKBOX) {
			return true;
		}
		return false;
	}

	public List<CmpUserTableDataOption> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<CmpUserTableDataOption> optionList) {
		this.optionList = optionList;
	}
}
package web.epp.action;

import java.util.List;

import com.hk.bean.CmpUserTableDataOption;
import com.hk.bean.CmpUserTableField;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

public class UserTableFieldValue {

	private CmpUserTableField cmpUserTableField;

	private String value;

	private List<CmpUserTableDataOption> selectedOptionList;

	public void setSelectedOptionList(
			List<CmpUserTableDataOption> selectedOptionList) {
		this.selectedOptionList = selectedOptionList;
	}

	public List<CmpUserTableDataOption> getSelectedOptionList() {
		return selectedOptionList;
	}

	public CmpUserTableField getCmpUserTableField() {
		return cmpUserTableField;
	}

	public void setCmpUserTableField(CmpUserTableField cmpUserTableField) {
		this.cmpUserTableField = cmpUserTableField;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int validate() {
		if (cmpUserTableField.isFieldText()) {
			if (!HkValidate.validateEmptyAndLength(this.value, true, 20)) {
				return Err.CMPUSERTABLEDATAVALUE_DATA_TEXT_ERROR;
			}
		}
		if (cmpUserTableField.isFieldTextArea()) {
			if (!HkValidate.validateEmptyAndLength(this.value, true, 500)) {
				return Err.CMPUSERTABLEDATAVALUE_DATA_TEXTAREA_ERROR;
			}
		}
		if (DataUtil.isEmpty(this.value)) {
			return Err.CMPUSERTABLEDATAVALUE_DATA_OPTION_ERROR;
		}
		return Err.SUCCESS;
	}
}
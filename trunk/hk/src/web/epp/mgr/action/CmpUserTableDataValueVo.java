package web.epp.mgr.action;

import java.util.List;

import web.epp.action.UserTableFieldValue;

public class CmpUserTableDataValueVo {

	List<UserTableFieldValue> userTableFieldValues;

	public CmpUserTableDataValueVo(
			List<UserTableFieldValue> userTableFieldValues) {
		this.userTableFieldValues = userTableFieldValues;
	}

	public List<UserTableFieldValue> getUserTableFieldValues() {
		return userTableFieldValues;
	}
}
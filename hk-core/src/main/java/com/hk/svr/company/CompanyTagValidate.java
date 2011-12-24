package com.hk.svr.company;

import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

public class CompanyTagValidate implements Err {
	private CompanyTagValidate() {//
	}

	public static int validateTag(String name, int kindId) {
		if (DataUtil.isEmpty(name)) {
			return COMPANYTAG_NAME_ERROR;
		}
		if (kindId < 1) {
			return COMPANYTAG_KIND_ERROR;
		}
		return SUCCESS;
	}
}
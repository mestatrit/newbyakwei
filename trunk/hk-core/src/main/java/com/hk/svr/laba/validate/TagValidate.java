package com.hk.svr.laba.validate;

import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

public class TagValidate implements Err {
	public static int validateTagName(String name) {
		if (!DataUtil.isNumberOrCharOrChinese(name)) {
			return TAG_NAME_ERROR;
		}
		return SUCCESS;
	}

	public static int validateTagNameWithSpace(String name) {
		if (!DataUtil.isNumberOrCharOrChineseWithSapce(name)) {
			return TAG_NAME_ERROR;
		}
		return SUCCESS;
	}
}
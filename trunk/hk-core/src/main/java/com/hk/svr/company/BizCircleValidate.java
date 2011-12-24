package com.hk.svr.company;

import com.hk.bean.BizCircle;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

public class BizCircleValidate implements Err {
	private BizCircleValidate() {//
	}

	public static int validateBizCircle(BizCircle bizCircle) {
		String name = DataUtil.toTextRow(bizCircle.getName());
		if (DataUtil.isEmpty(name)) {
			return BIZCIRCLE_NAME_ERROR;
		}
		if (name.length() > 50) {
			return BIZCIRCLE_NAME_LEN_TOOLONG;
		}
		if (bizCircle.getProvinceId() < 1 && bizCircle.getCityId() < 1) {
			return BIZCIRCLE_CITYID_ERROR;
		}
		return SUCCESS;
	}
}
package com.hk.svr.company;

import com.hk.bean.BuildingTag;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

public class BuildingTagValidate implements Err {
	private BuildingTagValidate() {//
	}

	public static int validateBuildingTag(BuildingTag buildingTag) {
		String name = DataUtil.toTextRow(buildingTag.getName());
		if (DataUtil.isEmpty(name)) {
			return BUILDINGTAG_NAME_ERROR;
		}
		if (name.length() > 30) {
			return BUILDINGTAG_LEN_TOOLONG;
		}
		if (buildingTag.getCityId() < 1 && buildingTag.getProvinceId() < 1) {
			return BUILDINGTAG_CITYID_ERROR;
		}
		return SUCCESS;
	}
}
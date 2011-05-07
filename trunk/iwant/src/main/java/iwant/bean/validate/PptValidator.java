package iwant.bean.validate;

import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

import cactus.util.HkValidate;

public class PptValidator {

	public static List<String> validate(Ppt ppt) {
		List<String> list = new ArrayList<String>();
		if (!HkValidate.validateEmptyAndLength(ppt.getName(), false, 20)) {
			list.add(Err.PPT_NAME_ERR);
		}
		return list;
	}

	public static List<String> validateMainPpt(MainPpt ppt) {
		List<String> list = new ArrayList<String>();
		if (!HkValidate.validateEmptyAndLength(ppt.getName(), false, 20)) {
			list.add(Err.PPT_NAME_ERR);
		}
		return list;
	}
}
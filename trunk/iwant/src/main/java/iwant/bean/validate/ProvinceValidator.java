package iwant.bean.validate;

import iwant.bean.Province;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

import cactus.util.HkValidate;

public class ProvinceValidator {

	public static List<String> validate(Province province) {
		List<String> list = new ArrayList<String>();
		if (!HkValidate.validateEmptyAndLength(province.getName(), false, 30)) {
			list.add(Err.PROVINCE_NAME_ERR);
		}
		return list;
	}
}
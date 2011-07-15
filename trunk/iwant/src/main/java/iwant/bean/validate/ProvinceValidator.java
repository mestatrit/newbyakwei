package iwant.bean.validate;

import halo.util.HaloValidate;
import iwant.bean.Province;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

public class ProvinceValidator {

	public static List<String> validate(Province province) {
		List<String> list = new ArrayList<String>();
		if (!HaloValidate.validateEmptyAndLength(province.getName(), false, 30)) {
			list.add(Err.PROVINCE_NAME_ERR);
		}
		return list;
	}
}
package iwant.bean.validate;

import halo.util.HaloValidate;
import iwant.bean.District;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

public class DistrictValidator {

	public static List<String> validate(District district) {
		List<String> list = new ArrayList<String>();
		if (!HaloValidate.validateEmptyAndLength(district.getName(), false, 30)) {
			list.add(Err.DISTRICT_NAME_ERR);
		}
		return list;
	}
}

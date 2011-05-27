package iwant.bean.validate;

import iwant.bean.District;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

import com.dev3g.cactus.util.HkValidate;

public class DistrictValidator {

	public static List<String> validate(District district) {
		List<String> list = new ArrayList<String>();
		if (!HkValidate.validateEmptyAndLength(district.getName(), false, 30)) {
			list.add(Err.DISTRICT_NAME_ERR);
		}
		return list;
	}
}

package iwant.bean.validate;

import halo.util.HaloValidate;
import iwant.bean.City;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

public class CityValidator {

	public static List<String> validate(City city) {
		List<String> list = new ArrayList<String>();
		if (!HaloValidate.validateEmptyAndLength(city.getName(), false, 30)) {
			list.add(Err.CITY_NAME_ERR);
		}
		return list;
	}
}

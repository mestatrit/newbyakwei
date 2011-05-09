package iwant.bean.validate;

import iwant.bean.City;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

import cactus.util.HkValidate;

public class CityValidator {

	public static List<String> validate(City city) {
		List<String> list = new ArrayList<String>();
		if (!HkValidate.validateEmptyAndLength(city.getName(), false, 30)) {
			list.add(Err.CITY_NAME_ERR);
		}
		return list;
	}
}

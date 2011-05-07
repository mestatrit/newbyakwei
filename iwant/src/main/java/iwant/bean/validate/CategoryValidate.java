package iwant.bean.validate;

import iwant.bean.Category;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

import cactus.util.DataUtil;

public class CategoryValidate {

	public static List<String> validate(Category category) {
		List<String> list = new ArrayList<String>();
		if (DataUtil.isEmpty(category.getName())
				|| category.getName().length() > 20) {
			list.add(Err.CATEGORY_NAME_ERR);
		}
		return list;
	}
}
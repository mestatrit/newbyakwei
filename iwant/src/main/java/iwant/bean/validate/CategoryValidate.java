package iwant.bean.validate;

import halo.util.DataUtil;
import iwant.bean.Category;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

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
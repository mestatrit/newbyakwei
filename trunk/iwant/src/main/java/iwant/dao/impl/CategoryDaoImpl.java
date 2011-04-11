package iwant.dao.impl;

import iwant.bean.Category;
import iwant.dao.CategoryDao;

import java.util.List;

import com.hk.frame.dao.query2.BaseDao;

public class CategoryDaoImpl extends BaseDao<Category> implements CategoryDao {

	@Override
	public Class<Category> getClazz() {
		return Category.class;
	}

	@Override
	public boolean isExistByName(String name) {
		if (this.count(null, "name=?", new Object[] { name }) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int countByNameAndNotCatid(String name, int catid) {
		return this.count(null, "name=? and catid!=?", new Object[] { name,
				catid });
	}

	@Override
	public List<Category> getListOrdered(int begin, int size) {
		return this.getList(null, null, null, "order_flag desc", begin, size);
	}
}
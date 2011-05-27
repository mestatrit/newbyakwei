package iwant.svr.impl;

import iwant.bean.Category;
import iwant.dao.CategoryDao;
import iwant.svr.CategorySvr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev3g.cactus.util.NumberUtil;

public class CategorySvrImpl implements CategorySvr {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public boolean createCategory(Category category) {
		if (this.categoryDao.isExistByName(category.getName())) {
			return false;
		}
		category.setCatid(NumberUtil.getInt(this.categoryDao.save(category)));
		category.setOrder_flag(category.getCatid());
		this.updateCategory(category);
		return true;
	}

	@Override
	public void deleteCategory(int catid) {
		this.categoryDao.deleteById(null, catid);
	}

	@Override
	public Category getCategory(int catid) {
		return this.categoryDao.getById(null, catid);
	}

	@Override
	public List<Category> getCategoryListForAll() {
		return this.categoryDao.getListOrdered(0, -1);
	}

	@Override
	public boolean updateCategory(Category category) {
		if (this.categoryDao.countByNameAndNotCatid(category.getName(),
				category.getCatid()) > 0) {
			return false;
		}
		this.categoryDao.update(category);
		return true;
	}

	@Override
	public void clearAllData() {
		this.categoryDao.deleteAllData();
	}
}
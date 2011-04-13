package iwant.svr;

import iwant.bean.Category;

import java.util.List;

public interface CategorySvr {

	/**
	 * 创建分类，初始顺序号与id相同
	 * 
	 * @param category
	 * @return true:创建成功,false:有重名
	 */
	boolean createCategory(Category category);

	boolean updateCategory(Category category);

	void deleteCategory(int catid);

	Category getCategory(int catid);

	List<Category> getCategoryListForAll();

	void clearAllData();
}
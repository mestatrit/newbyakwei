package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.Category;

import java.util.List;

public interface CategoryDao extends IDao<Category> {

	boolean isExistByName(String name);

	int countByNameAndNotCatid(String name, int catid);

	/**
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Category> getListOrdered(int begin, int size);

	void deleteAllData();
}

package iwant.dao;

import iwant.bean.Category;

import java.util.List;

import com.dev3g.cactus.dao.query.IDao;

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

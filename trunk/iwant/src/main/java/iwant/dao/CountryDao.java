package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.Country;

import java.util.List;

public interface CountryDao extends IDao<Country> {

	/**
	 * 获得所有国家数据
	 * 
	 * @return
	 */
	List<Country> getList();
}

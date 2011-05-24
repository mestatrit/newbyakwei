package iwant.dao;

import iwant.bean.Country;

import java.util.List;

import cactus.dao.query.IDao;

public interface CountryDao extends IDao<Country> {

	/**
	 * 获得所有国家数据
	 * 
	 * @return
	 */
	List<Country> getList();
}
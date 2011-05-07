package iwant.dao;

import iwant.bean.City;

import java.util.List;

import cactus.dao.query.IDao;

public interface CityDao extends IDao<City> {

	/**
	 * 获得省份下所有城市数据,按照发音排序
	 * 
	 * @param provinceid
	 * @return
	 */
	List<City> getListByProvinceid(int provinceid);
}
package iwant.dao;

import iwant.bean.Province;

import java.util.List;

import cactus.dao.query.IDao;

public interface ProvinceDao extends IDao<Province> {

	/**
	 * 获得某个国家下的省份数据,按照发音排序
	 * 
	 * @param countryid
	 * @return
	 */
	List<Province> getListByCountryid(int countryid);
}
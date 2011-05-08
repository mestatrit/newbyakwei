package iwant.dao.impl;

import iwant.bean.City;
import iwant.dao.CityDao;

import java.util.List;

import cactus.dao.query.BaseDao;

public class CityDaoImpl extends BaseDao<City> implements CityDao {

	@Override
	public Class<City> getClazz() {
		return City.class;
	}

	@Override
	public List<City> getListByProvinceid(int provinceid) {
		return this.getList(null, "provinceid=?", new Object[] { provinceid },
				"order_flg asc", 0, -1);
	}

	@Override
	public void deleteByProvinceid(int provinceid) {
		this.delete("provinceid=?", new Object[] { provinceid });
	}

	@Override
	public List<City> getList() {
		return this.getList(null, null, null, null, 0, -1);
	}

	@Override
	public boolean isExistByProvinceidAndName(int provinceid, String name) {
		if (this.count("provinceid=? and name=?", new Object[] { provinceid,
				name }) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistByProvinceidAndNameAndNotCityid(int provinceid,
			String name, int cityid) {
		if (this.count("provinceid=? and name=? and cityid!=?", new Object[] {
				provinceid, name, cityid }) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public City getByNameLike(String name) {
		return this.getObject("name like ?", new Object[] { "%" + name + "%" });
	}
}

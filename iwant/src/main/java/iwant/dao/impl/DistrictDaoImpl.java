package iwant.dao.impl;

import iwant.bean.District;
import iwant.dao.DistrictDao;

import java.util.List;

import cactus.dao.query.BaseDao;

public class DistrictDaoImpl extends BaseDao<District> implements DistrictDao {

	@Override
	public Class<District> getClazz() {
		return District.class;
	}

	@Override
	public void deleteByCityid(int cityid) {
		this.delete("cityid=?", new Object[] { cityid });
	}

	@Override
	public void deleteByProvinceid(int provinceid) {
		this.delete("provinceid=?", new Object[] { provinceid });
	}

	@Override
	public List<District> getListByCityid(int cityid) {
		return this.getList("cityid=?", new Object[] { cityid }, "did asc", 0,
				-1);
	}

	@Override
	public boolean isExistByCityidAndName(int cityid, String name) {
		if (this.count("cityid=? and name=?", new Object[] { cityid, name }) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistByCityidAndNameAndNotDid(int cityid, String name,
			int did) {
		if (this.count("cityid=? and name=? and did!=?", new Object[] { cityid,
				name, did }) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public District getByCityidAndNameLike(int cityid, String name) {
		return this.getObject("cityid=? and name like ?", new Object[] {
				cityid, "%" + name + "%" });
	}
}

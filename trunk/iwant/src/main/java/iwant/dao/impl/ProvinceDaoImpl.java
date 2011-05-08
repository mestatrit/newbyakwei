package iwant.dao.impl;

import iwant.bean.Province;
import iwant.dao.ProvinceDao;
import iwant.util.PinyinComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cactus.dao.query.BaseDao;

public class ProvinceDaoImpl extends BaseDao<Province> implements ProvinceDao {

	@Override
	public Class<Province> getClazz() {
		return Province.class;
	}

	@Override
	public List<Province> getListByCountryidOrderByOrder_flg(int countryid) {
		List<Province> list = this.getList(null, "countryid=?",
				new Object[] { countryid }, "order_flg asc", 0, -1);
		Collections.sort(list, new Comparator<Province>() {

			@Override
			public int compare(Province o1, Province o2) {
				return PinyinComparator.getInstance().compare(o1.getName(),
						o2.getName());
			}
		});
		return list;
	}

	@Override
	public boolean isExistByCountryidAndName(int countryid, String name) {
		if (this.count("countryid=? and name=?",
				new Object[] { countryid, name }) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistByCountryidAndNameAndNotProvinceid(int countryid,
			String name, int provinceid) {
		if (this.count("countryid=? and name=? and provinceid!=?",
				new Object[] { countryid, name, provinceid }) > 0) {
			return true;
		}
		return false;
	}
}
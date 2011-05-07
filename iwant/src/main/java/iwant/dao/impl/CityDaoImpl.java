package iwant.dao.impl;

import iwant.bean.City;
import iwant.dao.CityDao;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import cactus.dao.query.BaseDao;

public class CityDaoImpl extends BaseDao<City> implements CityDao {

	@Override
	public Class<City> getClazz() {
		return City.class;
	}

	@Override
	public List<City> getListByProvinceid(int provinceid) {
		List<City> list = this.getList(null, "provinceid=?",
				new Object[] { provinceid }, null, 0, -1);
		final Collator collator = Collator.getInstance(Locale.CHINA);
		Collections.sort(list, new Comparator<City>() {

			@Override
			public int compare(City o1, City o2) {
				return collator.compare(o1.getName(), o2.getName());
			}
		});
		return list;
	}
}

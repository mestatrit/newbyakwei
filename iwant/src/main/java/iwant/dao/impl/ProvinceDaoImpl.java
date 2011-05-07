package iwant.dao.impl;

import iwant.bean.Province;
import iwant.dao.ProvinceDao;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import cactus.dao.query.BaseDao;

public class ProvinceDaoImpl extends BaseDao<Province> implements ProvinceDao {

	@Override
	public Class<Province> getClazz() {
		return Province.class;
	}

	@Override
	public List<Province> getListByCountryid(int countryid) {
		List<Province> list = this.getList(null, "countryid=?",
				new Object[] { countryid }, null, 0, -1);
		final Collator collator = Collator.getInstance(Locale.CHINA);
		Collections.sort(list, new Comparator<Province>() {

			@Override
			public int compare(Province o1, Province o2) {
				return collator.compare(o1.getName(), o2.getName());
			}
		});
		return list;
	}
}
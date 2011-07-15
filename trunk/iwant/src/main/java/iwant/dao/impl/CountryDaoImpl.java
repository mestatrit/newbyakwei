package iwant.dao.impl;

import halo.dao.query.BaseDao;
import iwant.bean.Country;
import iwant.dao.CountryDao;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("countryDao")
public class CountryDaoImpl extends BaseDao<Country> implements CountryDao {

	@Override
	public Class<Country> getClazz() {
		return Country.class;
	}

	@Override
	public List<Country> getList() {
		return this.getList(null, null, null, null, 0, -1);
	}
}

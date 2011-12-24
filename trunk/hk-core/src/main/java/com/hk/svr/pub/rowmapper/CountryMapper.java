package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.Country;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CountryMapper extends HkRowMapper<Country> {

	@Override
	public Class<Country> getMapperClass() {
		return Country.class;
	}

	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
		Country c = new Country();
		c.setCountryId(rs.getInt("countryid"));
		c.setCountry(rs.getString("country"));
		c.setName(rs.getString("name"));
		return c;
	}
}
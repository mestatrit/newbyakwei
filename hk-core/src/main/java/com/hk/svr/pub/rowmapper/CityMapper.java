package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.City;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CityMapper extends HkRowMapper<City> {

	@Override
	public Class<City> getMapperClass() {
		return City.class;
	}

	public City mapRow(ResultSet rs, int rowNum) throws SQLException {
		City c = new City();
		c.setCityId(rs.getInt("cityid"));
		c.setProvinceId(rs.getInt("provinceid"));
		c.setCountryId(rs.getInt("countryid"));
		c.setCity(rs.getString("city"));
		c.setName(rs.getString("name"));
		return c;
	}
}
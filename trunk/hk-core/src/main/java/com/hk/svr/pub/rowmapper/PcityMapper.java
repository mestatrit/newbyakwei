package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Pcity;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class PcityMapper extends HkRowMapper<Pcity> {
	@Override
	public Class<Pcity> getMapperClass() {
		return Pcity.class;
	}

	public Pcity mapRow(ResultSet rs, int rowNum) throws SQLException {
		Pcity c = new Pcity();
		c.setCityId(rs.getInt("cityid"));
		c.setProvinceId(rs.getInt("provinceid"));
		c.setName(rs.getString("name"));
		c.setCountryId(rs.getInt("countryid"));
		c.setOcityId(rs.getInt("ocityid"));
		return c;
	}
}
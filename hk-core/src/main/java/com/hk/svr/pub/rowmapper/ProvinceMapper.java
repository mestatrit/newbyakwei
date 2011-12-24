package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Province;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ProvinceMapper extends HkRowMapper<Province> {

	@Override
	public Class<Province> getMapperClass() {
		return Province.class;
	}

	public Province mapRow(ResultSet rs, int rowNum) throws SQLException {
		Province p = new Province();
		p.setProvinceId(rs.getInt("provinceid"));
		p.setCountryId(rs.getInt("countryid"));
		p.setProvince(rs.getString("province"));
		p.setName(rs.getString("name"));
		return p;
	}
}
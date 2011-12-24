package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CityCode;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CityCodeMapper extends HkRowMapper<CityCode> {
	@Override
	public Class<CityCode> getMapperClass() {
		return CityCode.class;
	}

	public CityCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		CityCode o = new CityCode();
		o.setCodeId(rs.getInt("codeid"));
		o.setName(rs.getString("name"));
		o.setCode(rs.getString("code"));
		return o;
	}
}
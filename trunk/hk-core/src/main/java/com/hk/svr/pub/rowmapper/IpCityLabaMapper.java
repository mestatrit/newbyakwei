package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.IpCityLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class IpCityLabaMapper extends HkRowMapper<IpCityLaba> {
	@Override
	public Class<IpCityLaba> getMapperClass() {
		return IpCityLaba.class;
	}

	public IpCityLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpCityLaba o = new IpCityLaba();
		o.setCityId(rs.getInt("cityid"));
		o.setLabaId(rs.getLong("labaid"));
		return o;
	}
}
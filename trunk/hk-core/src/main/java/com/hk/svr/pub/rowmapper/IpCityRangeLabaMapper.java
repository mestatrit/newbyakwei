package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.IpCityRangeLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class IpCityRangeLabaMapper extends HkRowMapper<IpCityRangeLaba> {
	@Override
	public Class<IpCityRangeLaba> getMapperClass() {
		return IpCityRangeLaba.class;
	}

	public IpCityRangeLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpCityRangeLaba o = new IpCityRangeLaba();
		o.setRangeId(rs.getInt("rangeid"));
		o.setLabaId(rs.getLong("labaid"));
		return o;
	}
}
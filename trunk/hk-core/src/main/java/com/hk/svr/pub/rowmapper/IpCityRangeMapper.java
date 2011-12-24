package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.IpCityRange;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class IpCityRangeMapper extends HkRowMapper<IpCityRange> {
	@Override
	public Class<IpCityRange> getMapperClass() {
		return IpCityRange.class;
	}

	public IpCityRange mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpCityRange o = new IpCityRange();
		o.setRangeId(rs.getInt("rangeid"));
		o.setBeginIp(rs.getLong("beginip"));
		o.setEndIp(rs.getLong("endip"));
		o.setCityId(rs.getInt("cityid"));
		return o;
	}
}
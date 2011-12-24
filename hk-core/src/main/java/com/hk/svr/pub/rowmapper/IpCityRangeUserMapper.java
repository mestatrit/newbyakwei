package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.IpCityRangeUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class IpCityRangeUserMapper extends HkRowMapper<IpCityRangeUser> {
	@Override
	public Class<IpCityRangeUser> getMapperClass() {
		return IpCityRangeUser.class;
	}

	public IpCityRangeUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpCityRangeUser o = new IpCityRangeUser();
		o.setRangeId(rs.getInt("rangeid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
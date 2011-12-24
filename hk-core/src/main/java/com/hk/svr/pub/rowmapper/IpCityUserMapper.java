package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.IpCityUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class IpCityUserMapper extends HkRowMapper<IpCityUser> {
	@Override
	public Class<IpCityUser> getMapperClass() {
		return IpCityUser.class;
	}

	public IpCityUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpCityUser o = new IpCityUser();
		o.setCityId(rs.getInt("cityid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
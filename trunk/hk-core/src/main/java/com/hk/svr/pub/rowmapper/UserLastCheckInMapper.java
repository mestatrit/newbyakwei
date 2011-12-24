package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserLastCheckIn;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserLastCheckInMapper extends HkRowMapper<UserLastCheckIn> {

	@Override
	public Class<UserLastCheckIn> getMapperClass() {
		return UserLastCheckIn.class;
	}

	public UserLastCheckIn mapRow(ResultSet rs, int arg1) throws SQLException {
		UserLastCheckIn o = new UserLastCheckIn();
		o.setUserId(rs.getLong("userid"));
		o.setData(rs.getString("data"));
		return o;
	}
}
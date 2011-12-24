package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserDateCheckInCmp;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserDateCheckInCmpMapper extends HkRowMapper<UserDateCheckInCmp> {

	@Override
	public Class<UserDateCheckInCmp> getMapperClass() {
		return UserDateCheckInCmp.class;
	}

	public UserDateCheckInCmp mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		UserDateCheckInCmp o = new UserDateCheckInCmp();
		o.setUserId(rs.getLong("userid"));
		o.setUptime(rs.getTimestamp("uptime"));
		o.setData(rs.getString("data"));
		return o;
	}
}
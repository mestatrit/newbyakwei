package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserFgtSms;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserFgtSmsMapper extends HkRowMapper<UserFgtSms> {

	@Override
	public Class<UserFgtSms> getMapperClass() {
		return UserFgtSms.class;
	}

	public UserFgtSms mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserFgtSms o = new UserFgtSms();
		o.setUserId(rs.getLong("userid"));
		o.setSmscode(rs.getString("smscode"));
		o.setSendCount(rs.getInt("sendcount"));
		o.setUptime(rs.getTimestamp("uptime"));
		return o;
	}
}
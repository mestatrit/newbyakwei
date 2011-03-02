package com.hk.frame.dao.query.criteria.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkQuery_UserMapper extends HkRowMapper<HkQuery_User> {

	@Override
	public Class<HkQuery_User> getMapperClass() {
		return HkQuery_User.class;
	}

	@Override
	public HkQuery_User mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkQuery_User o = new HkQuery_User();
		o.setUserid(rs.getLong("userid"));
		o.setNick(rs.getString("nick"));
		o.setAge(rs.getInt("age"));
		o.setGender(rs.getByte("gender"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
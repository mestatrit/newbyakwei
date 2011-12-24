package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserFgtMail;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserFgtMailMapper extends HkRowMapper<UserFgtMail> {
	@Override
	public Class<UserFgtMail> getMapperClass() {
		return UserFgtMail.class;
	}

	public UserFgtMail mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserFgtMail o = new UserFgtMail();
		o.setUserId(rs.getLong("userid"));
		o.setSencCount(rs.getInt("sendcount"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setDesValue(rs.getString("desvalue"));
		return o;
	}
}
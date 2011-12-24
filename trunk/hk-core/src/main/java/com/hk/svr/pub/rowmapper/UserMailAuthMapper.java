package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserMailAuth;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserMailAuthMapper extends HkRowMapper<UserMailAuth> {
	@Override
	public Class<UserMailAuth> getMapperClass() {
		return UserMailAuth.class;
	}

	public UserMailAuth mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserMailAuth o = new UserMailAuth();
		o.setUserId(rs.getLong("userid"));
		o.setAuthcode(rs.getString("authcode"));
		o.setOverTime(rs.getTimestamp("overtime"));
		return o;
	}
}
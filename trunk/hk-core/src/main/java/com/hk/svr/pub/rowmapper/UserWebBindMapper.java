package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserWebBind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserWebBindMapper extends HkRowMapper<UserWebBind> {
	@Override
	public Class<UserWebBind> getMapperClass() {
		return UserWebBind.class;
	}

	public UserWebBind mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserWebBind o = new UserWebBind();
		o.setUserId(rs.getLong("userid"));
		o.setBoseeId(rs.getString("boseeid"));
		return o;
	}
}
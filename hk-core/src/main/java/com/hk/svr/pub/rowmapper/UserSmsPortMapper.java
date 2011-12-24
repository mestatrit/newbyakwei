package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserSmsPort;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserSmsPortMapper extends HkRowMapper<UserSmsPort> {
	@Override
	public Class<UserSmsPort> getMapperClass() {
		return UserSmsPort.class;
	}

	public UserSmsPort mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserSmsPort o = new UserSmsPort();
		o.setSysId(rs.getLong("sysid"));
		o.setPort(rs.getString("port"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
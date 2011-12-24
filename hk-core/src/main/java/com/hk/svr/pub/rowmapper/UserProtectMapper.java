package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserProtect;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserProtectMapper extends HkRowMapper<UserProtect> {
	@Override
	public Class<UserProtect> getMapperClass() {
		return UserProtect.class;
	}

	public UserProtect mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserProtect o = new UserProtect();
		o.setUserId(rs.getLong("userid"));
		o.setPconfig(rs.getInt("pconfig"));
		o.setPvalue(rs.getString("pvalue"));
		return o;
	}
}
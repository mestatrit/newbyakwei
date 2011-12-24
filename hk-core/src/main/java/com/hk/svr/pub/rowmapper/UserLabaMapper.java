package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserLabaMapper extends HkRowMapper<UserLaba> {
	@Override
	public Class<UserLaba> getMapperClass() {
		return UserLaba.class;
	}

	public UserLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserLaba o = new UserLaba();
		o.setLabaId(rs.getLong("labaid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
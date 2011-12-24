package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserEquPoint;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserEquPointMapper extends HkRowMapper<UserEquPoint> {

	@Override
	public Class<UserEquPoint> getMapperClass() {
		return UserEquPoint.class;
	}

	public UserEquPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserEquPoint o = new UserEquPoint();
		o.setUserId(rs.getLong("userid"));
		o.setPoints(rs.getInt("points"));
		return o;
	}
}
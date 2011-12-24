package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserRecentUpdate;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserRecentUpdateMapper extends HkRowMapper<UserRecentUpdate> {
	@Override
	public Class<UserRecentUpdate> getMapperClass() {
		return UserRecentUpdate.class;
	}

	public UserRecentUpdate mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		UserRecentUpdate o = new UserRecentUpdate();
		o.setUserId(rs.getLong("userid"));
		o.setLast30LabaCount(rs.getInt("last30labacount"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserUpdate;
import com.hk.frame.dao.rowmapper.HkRowMapper;
import com.hk.frame.util.P;

public class UserUpdateMapper extends HkRowMapper<UserUpdate> {
	@Override
	public Class<UserUpdate> getMapperClass() {
		return UserUpdate.class;
	}

	public UserUpdate mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserUpdate o = new UserUpdate();
		o.setUserId(rs.getLong("userid"));
		o.setUptime(rs.getLong("uptime"));
		return o;
	}

	public static void main(String[] args) {
		for (int i = 1; i < 300; i++) {
			P.print(i);
			P.print(",");
		}
	}
}
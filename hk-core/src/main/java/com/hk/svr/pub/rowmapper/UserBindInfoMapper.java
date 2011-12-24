package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserBindInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserBindInfoMapper extends HkRowMapper<UserBindInfo> {
	@Override
	public Class<UserBindInfo> getMapperClass() {
		return UserBindInfo.class;
	}

	public UserBindInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserBindInfo o = new UserBindInfo();
		o.setUserId(rs.getLong("userid"));
		o.setMsn(rs.getString("msn"));
		return o;
	}
}
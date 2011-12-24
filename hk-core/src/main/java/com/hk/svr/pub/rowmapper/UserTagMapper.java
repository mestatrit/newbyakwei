package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserTagMapper extends HkRowMapper<UserTag> {
	@Override
	public Class<UserTag> getMapperClass() {
		return UserTag.class;
	}

	public UserTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserTag o = new UserTag();
		o.setSysId(rs.getLong("sysid"));
		o.setUserId(rs.getLong("userid"));
		o.setTagId(rs.getLong("tagid"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserAuthorTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserAuthorTagMapper extends HkRowMapper<UserAuthorTag> {
	@Override
	public Class<UserAuthorTag> getMapperClass() {
		return UserAuthorTag.class;
	}

	public UserAuthorTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserAuthorTag o = new UserAuthorTag();
		o.setUserId(rs.getLong("userid"));
		o.setTagId(rs.getLong("tagid"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.AuthorTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class AuthorTagMapper extends HkRowMapper<AuthorTag> {
	@Override
	public Class<AuthorTag> getMapperClass() {
		return AuthorTag.class;
	}

	public AuthorTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		AuthorTag o = new AuthorTag();
		o.setTagId(rs.getLong("tagid"));
		o.setName(rs.getString("name"));
		return o;
	}
}

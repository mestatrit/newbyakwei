package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.AuthorApply;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class AuthorApplyMapper extends HkRowMapper<AuthorApply> {
	@Override
	public Class<AuthorApply> getMapperClass() {
		return AuthorApply.class;
	}

	public AuthorApply mapRow(ResultSet rs, int rowNum) throws SQLException {
		AuthorApply o = new AuthorApply();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setName(rs.getString("name"));
		o.setTel(rs.getString("tel"));
		o.setEmail(rs.getString("email"));
		o.setBlog(rs.getString("blog"));
		o.setCheckflg(rs.getByte("checkflg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setContent(rs.getString("content"));
		return o;
	}
}
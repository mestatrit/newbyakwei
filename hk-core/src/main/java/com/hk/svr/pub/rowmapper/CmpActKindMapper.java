package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActKindMapper extends HkRowMapper<CmpActKind> {
	@Override
	public Class<CmpActKind> getMapperClass() {
		return CmpActKind.class;
	}

	public CmpActKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActKind o = new CmpActKind();
		o.setKindId(rs.getLong("kindid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
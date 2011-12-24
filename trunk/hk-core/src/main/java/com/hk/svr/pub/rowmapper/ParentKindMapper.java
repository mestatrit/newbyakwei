package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.ParentKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ParentKindMapper extends HkRowMapper<ParentKind> {
	@Override
	public Class<ParentKind> getMapperClass() {
		return ParentKind.class;
	}

	public ParentKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		ParentKind o = new ParentKind();
		o.setKindId(rs.getInt("kindid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
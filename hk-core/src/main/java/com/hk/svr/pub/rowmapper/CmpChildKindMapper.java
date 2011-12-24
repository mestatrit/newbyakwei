package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpChildKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpChildKindMapper extends HkRowMapper<CmpChildKind> {
	@Override
	public Class<CmpChildKind> getMapperClass() {
		return CmpChildKind.class;
	}

	public CmpChildKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpChildKind o = new CmpChildKind();
		o.setOid(rs.getInt("oid"));
		o.setKindId(rs.getInt("kindid"));
		o.setName(rs.getString("name"));
		o.setCmpCount(rs.getInt("cmpcount"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUnionKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUnionKindMapper extends HkRowMapper<CmpUnionKind> {
	@Override
	public Class<CmpUnionKind> getMapperClass() {
		return CmpUnionKind.class;
	}

	public CmpUnionKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpUnionKind o = new CmpUnionKind();
		o.setKindId(rs.getLong("kindid"));
		o.setUid(rs.getLong("uid"));
		o.setName(rs.getString("name"));
		o.setKindLevel(rs.getInt("kindlevel"));
		o.setParentId(rs.getLong("parentid"));
		o.setOrderflg(rs.getInt("orderflg"));
		o.setHasChildflg(rs.getByte("haschildflg"));
		return o;
	}
}
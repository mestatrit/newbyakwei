package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUnionCmdKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUnionCmdKindMapper extends HkRowMapper<CmpUnionCmdKind> {
	@Override
	public Class<CmpUnionCmdKind> getMapperClass() {
		return CmpUnionCmdKind.class;
	}

	public CmpUnionCmdKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpUnionCmdKind o = new CmpUnionCmdKind();
		o.setOid(rs.getLong("oid"));
		o.setUid(rs.getLong("uid"));
		o.setKindId(rs.getLong("kindid"));
		return o;
	}
}
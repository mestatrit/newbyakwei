package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActorPink;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActorPinkMapper extends HkRowMapper<CmpActorPink> {

	@Override
	public Class<CmpActorPink> getMapperClass() {
		return CmpActorPink.class;
	}

	public CmpActorPink mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActorPink o = new CmpActorPink();
		o.setOid(rs.getLong("oid"));
		o.setActorId(rs.getLong("actorid"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActorSvrRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActorSvrRefMapper extends HkRowMapper<CmpActorSvrRef> {

	@Override
	public Class<CmpActorSvrRef> getMapperClass() {
		return CmpActorSvrRef.class;
	}

	public CmpActorSvrRef mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActorSvrRef o = new CmpActorSvrRef();
		o.setOid(rs.getLong("oid"));
		o.setActorId(rs.getLong("actorid"));
		o.setSvrId(rs.getLong("svrid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
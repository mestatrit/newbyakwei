package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpAdRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpAdRefMapper extends HkRowMapper<CmpAdRef> {

	@Override
	public Class<CmpAdRef> getMapperClass() {
		return CmpAdRef.class;
	}

	public CmpAdRef mapRow(ResultSet rs, int arg1) throws SQLException {
		CmpAdRef o = new CmpAdRef();
		o.setOid(rs.getLong("oid"));
		o.setAdid(rs.getLong("adid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
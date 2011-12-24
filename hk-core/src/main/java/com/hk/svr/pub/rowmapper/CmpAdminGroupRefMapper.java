package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpAdminGroupRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpAdminGroupRefMapper extends HkRowMapper<CmpAdminGroupRef> {
	@Override
	public Class<CmpAdminGroupRef> getMapperClass() {
		return CmpAdminGroupRef.class;
	}

	public CmpAdminGroupRef mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpAdminGroupRef o = new CmpAdminGroupRef();
		o.setOid(rs.getLong("oid"));
		o.setGroupId(rs.getLong("groupid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
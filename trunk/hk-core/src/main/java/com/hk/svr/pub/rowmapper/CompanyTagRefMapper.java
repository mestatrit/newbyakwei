package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyTagRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyTagRefMapper extends HkRowMapper<CompanyTagRef> {
	@Override
	public Class<CompanyTagRef> getMapperClass() {
		return CompanyTagRef.class;
	}

	public CompanyTagRef mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyTagRef o = new CompanyTagRef();
		o.setCompanyId(rs.getLong("companyid"));
		o.setTagId(rs.getInt("tagid"));
		o.setOid(rs.getLong("oid"));
		return o;
	}
}
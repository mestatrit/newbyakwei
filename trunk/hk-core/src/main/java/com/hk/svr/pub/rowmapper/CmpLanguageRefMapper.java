package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpLanguageRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpLanguageRefMapper extends HkRowMapper<CmpLanguageRef> {

	@Override
	public Class<CmpLanguageRef> getMapperClass() {
		return CmpLanguageRef.class;
	}

	public CmpLanguageRef mapRow(ResultSet rs, int arg1) throws SQLException {
		CmpLanguageRef o = new CmpLanguageRef();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setRefCompanyId(rs.getLong("refcompanyid"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpSvrKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpSvrKindMapper extends HkRowMapper<CmpSvrKind> {

	@Override
	public Class<CmpSvrKind> getMapperClass() {
		return CmpSvrKind.class;
	}

	public CmpSvrKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpSvrKind o = new CmpSvrKind();
		o.setKindId(rs.getLong("kindid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpBbsKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBbsKindMapper extends HkRowMapper<CmpBbsKind> {

	@Override
	public Class<CmpBbsKind> getMapperClass() {
		return CmpBbsKind.class;
	}

	public CmpBbsKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpBbsKind o = new CmpBbsKind();
		o.setKindId(rs.getLong("kindid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setMustpic(rs.getByte("mustpic"));
		return o;
	}
}
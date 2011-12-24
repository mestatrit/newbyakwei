package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpSellNetKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpSellNetKindMapper extends HkRowMapper<CmpSellNetKind> {

	@Override
	public Class<CmpSellNetKind> getMapperClass() {
		return CmpSellNetKind.class;
	}

	public CmpSellNetKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpSellNetKind o = new CmpSellNetKind();
		o.setKindId(rs.getLong("kindid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
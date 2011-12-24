package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyKindMapper extends HkRowMapper<CompanyKind> {
	@Override
	public Class<CompanyKind> getMapperClass() {
		return CompanyKind.class;
	}

	public CompanyKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyKind o = new CompanyKind();
		o.setKindId(rs.getInt("kindid"));
		o.setName(rs.getString("name"));
		o.setPriceTip(rs.getInt("pricetip"));
		o.setParentId(rs.getInt("parentid"));
		o.setCmpCount(rs.getInt("cmpcount"));
		o.setOrderflg(rs.getInt("orderflg"));
		return o;
	}
}
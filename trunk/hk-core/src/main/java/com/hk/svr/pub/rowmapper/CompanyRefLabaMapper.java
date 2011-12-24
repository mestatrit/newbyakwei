package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyRefLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyRefLabaMapper extends HkRowMapper<CompanyRefLaba> {
	@Override
	public Class<CompanyRefLaba> getMapperClass() {
		return CompanyRefLaba.class;
	}

	public CompanyRefLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyRefLaba o = new CompanyRefLaba();
		o.setCompanyId(rs.getLong("companyid"));
		o.setLabaId(rs.getLong("labaid"));
		return o;
	}
}
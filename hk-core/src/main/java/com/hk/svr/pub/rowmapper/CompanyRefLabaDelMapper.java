package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyRefLabaDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyRefLabaDelMapper extends HkRowMapper<CompanyRefLabaDel> {
	@Override
	public Class<CompanyRefLabaDel> getMapperClass() {
		return CompanyRefLabaDel.class;
	}

	public CompanyRefLabaDel mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CompanyRefLabaDel o = new CompanyRefLabaDel();
		o.setCompanyId(rs.getLong("companyid"));
		o.setLabaId(rs.getLong("labaid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}
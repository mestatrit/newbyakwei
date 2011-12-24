package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyBizCircle;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyBizCircleMapper extends HkRowMapper<CompanyBizCircle> {
	@Override
	public Class<CompanyBizCircle> getMapperClass() {
		return CompanyBizCircle.class;
	}

	public CompanyBizCircle mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CompanyBizCircle o = new CompanyBizCircle();
		o.setCompanyId(rs.getLong("companyid"));
		o.setCircleId(rs.getInt("circleid"));
		return o;
	}
}
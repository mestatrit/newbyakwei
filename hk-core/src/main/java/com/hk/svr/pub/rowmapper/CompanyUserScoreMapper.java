package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyUserScore;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyUserScoreMapper extends HkRowMapper<CompanyUserScore> {
	@Override
	public Class<CompanyUserScore> getMapperClass() {
		return CompanyUserScore.class;
	}

	public CompanyUserScore mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CompanyUserScore o = new CompanyUserScore();
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setScore(rs.getInt("score"));
		return o;
	}
}
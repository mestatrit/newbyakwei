package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductUserScore;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductUserScoreMapper extends HkRowMapper<CmpProductUserScore> {
	@Override
	public Class<CmpProductUserScore> getMapperClass() {
		return CmpProductUserScore.class;
	}

	public CmpProductUserScore mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpProductUserScore o = new CmpProductUserScore();
		o.setUserId(rs.getLong("userid"));
		o.setProductId(rs.getLong("productid"));
		o.setScore(rs.getInt("score"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
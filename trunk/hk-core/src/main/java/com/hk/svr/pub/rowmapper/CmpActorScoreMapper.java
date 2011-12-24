package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActorScore;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActorScoreMapper extends HkRowMapper<CmpActorScore> {

	@Override
	public Class<CmpActorScore> getMapperClass() {
		return CmpActorScore.class;
	}

	public CmpActorScore mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActorScore o = new CmpActorScore();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setActorId(rs.getLong("actorid"));
		o.setUserId(rs.getLong("userid"));
		o.setScore(rs.getInt("score"));
		return o;
	}
}
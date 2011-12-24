package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActorCmt;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActorCmtMapper extends HkRowMapper<CmpActorCmt> {

	@Override
	public Class<CmpActorCmt> getMapperClass() {
		return CmpActorCmt.class;
	}

	public CmpActorCmt mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActorCmt o = new CmpActorCmt();
		o.setCmtId(rs.getLong("cmtid"));
		o.setUserId(rs.getLong("userid"));
		o.setActorId(rs.getLong("actorid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setScore(rs.getInt("score"));
		return o;
	}
}
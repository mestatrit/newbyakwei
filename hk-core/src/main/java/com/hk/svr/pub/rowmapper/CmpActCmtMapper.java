package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActCmt;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActCmtMapper extends HkRowMapper<CmpActCmt> {
	@Override
	public Class<CmpActCmt> getMapperClass() {
		return CmpActCmt.class;
	}

	public CmpActCmt mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActCmt o = new CmpActCmt();
		o.setCmtId(rs.getLong("cmtid"));
		o.setActId(rs.getLong("actid"));
		o.setUserId(rs.getLong("userid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
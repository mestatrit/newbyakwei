package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyReview;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyReviewMapper extends HkRowMapper<CompanyReview> {
	@Override
	public Class<CompanyReview> getMapperClass() {
		return CompanyReview.class;
	}

	public CompanyReview mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyReview o = new CompanyReview();
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setScore(rs.getInt("score"));
		o.setContent(rs.getString("content"));
		o.setLongContent(rs.getString("longcontent"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setLabaId(rs.getLong("labaid"));
		o.setSendFrom(rs.getInt("sendfrom"));
		o.setCheckflg(rs.getByte("checkflg"));
		return o;
	}
}
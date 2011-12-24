package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductReview;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductReviewMapper extends HkRowMapper<CmpProductReview> {
	@Override
	public Class<CmpProductReview> getMapperClass() {
		return CmpProductReview.class;
	}

	public CmpProductReview mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpProductReview o = new CmpProductReview();
		o.setLabaId(rs.getLong("labaid"));
		o.setUserId(rs.getLong("userid"));
		o.setScore(rs.getInt("score"));
		o.setContent(rs.getString("content"));
		o.setLongContent(rs.getString("longcontent"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setSendFrom(rs.getInt("sendfrom"));
		o.setCheckflg(rs.getByte("checkflg"));
		o.setProductId(rs.getInt("productid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
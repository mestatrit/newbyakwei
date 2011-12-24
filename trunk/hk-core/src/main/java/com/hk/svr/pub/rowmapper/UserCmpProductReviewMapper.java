package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserCmpProductReview;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserCmpProductReviewMapper extends
		HkRowMapper<UserCmpProductReview> {
	@Override
	public Class<UserCmpProductReview> getMapperClass() {
		return UserCmpProductReview.class;
	}

	public UserCmpProductReview mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		UserCmpProductReview o = new UserCmpProductReview();
		o.setUserId(rs.getLong("userid"));
		o.setProductId(rs.getInt("productid"));
		o.setLabaId(rs.getLong("labaid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
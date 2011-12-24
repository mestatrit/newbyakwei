package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserCmpReview;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserCmpReviewMapper extends HkRowMapper<UserCmpReview> {
	@Override
	public Class<UserCmpReview> getMapperClass() {
		return UserCmpReview.class;
	}

	public UserCmpReview mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCmpReview o = new UserCmpReview();
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setLabaId(rs.getLong("labaid"));
		return o;
	}
}

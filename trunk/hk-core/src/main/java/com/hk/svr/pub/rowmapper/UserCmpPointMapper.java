package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserCmpPoint;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserCmpPointMapper extends HkRowMapper<UserCmpPoint> {

	@Override
	public Class<UserCmpPoint> getMapperClass() {
		return UserCmpPoint.class;
	}

	public UserCmpPoint mapRow(ResultSet rs, int arg1) throws SQLException {
		UserCmpPoint o = new UserCmpPoint();
		o.setCompanyId(rs.getLong("companyid"));
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setPoints(rs.getInt("points"));
		return o;
	}
}
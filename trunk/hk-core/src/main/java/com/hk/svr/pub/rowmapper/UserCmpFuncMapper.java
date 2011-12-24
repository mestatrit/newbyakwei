package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserCmpFunc;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserCmpFuncMapper extends HkRowMapper<UserCmpFunc> {

	@Override
	public Class<UserCmpFunc> getMapperClass() {
		return UserCmpFunc.class;
	}

	public UserCmpFunc mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCmpFunc o = new UserCmpFunc();
		o.setUserId(rs.getLong("userid"));
		o.setBoxflg(rs.getByte("boxflg"));
		o.setCouponflg(rs.getByte("couponflg"));
		o.setAdflg(rs.getByte("adflg"));
		return o;
	}
}
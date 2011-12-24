package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserCmpEnjoy;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserCmpEnjoyMapper extends HkRowMapper<UserCmpEnjoy> {

	@Override
	public Class<UserCmpEnjoy> getMapperClass() {
		return UserCmpEnjoy.class;
	}

	public UserCmpEnjoy mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCmpEnjoy o = new UserCmpEnjoy();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUeid(rs.getLong("ueid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
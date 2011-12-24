package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserEquEnjoy;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserEquEnjoyMapper extends HkRowMapper<UserEquEnjoy> {

	@Override
	public Class<UserEquEnjoy> getMapperClass() {
		return UserEquEnjoy.class;
	}

	public UserEquEnjoy mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserEquEnjoy o = new UserEquEnjoy();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setEnjoyUserId(rs.getLong("enjoyuserid"));
		o.setUeid(rs.getLong("ueid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setForceEid(rs.getLong("forceeid"));
		return o;
	}
}
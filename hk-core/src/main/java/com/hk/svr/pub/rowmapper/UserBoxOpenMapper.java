package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserBoxOpen;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserBoxOpenMapper extends HkRowMapper<UserBoxOpen> {

	@Override
	public Class<UserBoxOpen> getMapperClass() {
		return UserBoxOpen.class;
	}

	public UserBoxOpen mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserBoxOpen o = new UserBoxOpen();
		o.setSysId(rs.getLong("sysid"));
		o.setBoxId(rs.getLong("boxid"));
		o.setUserId(rs.getLong("userid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setPrizeId(rs.getLong("prizeid"));
		return o;
	}
}
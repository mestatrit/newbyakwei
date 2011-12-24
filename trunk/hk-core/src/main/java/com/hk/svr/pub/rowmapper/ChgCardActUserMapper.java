package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.ChgCardActUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ChgCardActUserMapper extends HkRowMapper<ChgCardActUser> {
	@Override
	public Class<ChgCardActUser> getMapperClass() {
		return ChgCardActUser.class;
	}

	public ChgCardActUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChgCardActUser o = new ChgCardActUser();
		o.setActId(rs.getLong("actid"));
		o.setUserId(rs.getLong("userid"));
		o.setSysId(rs.getLong("sysid"));
		return o;
	}
}
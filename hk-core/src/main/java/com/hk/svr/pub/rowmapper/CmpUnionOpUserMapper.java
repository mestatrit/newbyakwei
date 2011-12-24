package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUnionOpUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUnionOpUserMapper extends HkRowMapper<CmpUnionOpUser> {
	@Override
	public Class<CmpUnionOpUser> getMapperClass() {
		return CmpUnionOpUser.class;
	}

	public CmpUnionOpUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpUnionOpUser o = new CmpUnionOpUser();
		o.setUid(rs.getLong("uid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.HandleCheckInUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HandleCheckInUserMapper extends HkRowMapper<HandleCheckInUser> {
	@Override
	public Class<HandleCheckInUser> getMapperClass() {
		return HandleCheckInUser.class;
	}

	public HandleCheckInUser mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		HandleCheckInUser o = new HandleCheckInUser();
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setOid(rs.getLong("oid"));
		return o;
	}
}
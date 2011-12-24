package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.WelProUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class WelProUserMapper extends HkRowMapper<WelProUser> {
	@Override
	public Class<WelProUser> getMapperClass() {
		return WelProUser.class;
	}

	public WelProUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		WelProUser o = new WelProUser();
		o.setOid(rs.getLong("oid"));
		o.setProuserId(rs.getLong("prouserid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
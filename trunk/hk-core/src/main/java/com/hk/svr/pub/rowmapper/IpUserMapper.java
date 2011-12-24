package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.IpUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class IpUserMapper extends HkRowMapper<IpUser> {
	@Override
	public Class<IpUser> getMapperClass() {
		return IpUser.class;
	}

	public IpUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpUser o = new IpUser();
		o.setIpnumber(rs.getLong("ipnumber"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
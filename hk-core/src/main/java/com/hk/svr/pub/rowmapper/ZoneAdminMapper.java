package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.ZoneAdmin;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ZoneAdminMapper extends HkRowMapper<ZoneAdmin> {
	@Override
	public Class<ZoneAdmin> getMapperClass() {
		return ZoneAdmin.class;
	}

	public ZoneAdmin mapRow(ResultSet rs, int rowNum) throws SQLException {
		ZoneAdmin o = new ZoneAdmin();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setPcityId(rs.getInt("pcityid"));
		return o;
	}
}

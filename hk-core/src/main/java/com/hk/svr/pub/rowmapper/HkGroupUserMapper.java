package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkGroupUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkGroupUserMapper extends HkRowMapper<HkGroupUser> {
	@Override
	public Class<HkGroupUser> getMapperClass() {
		return HkGroupUser.class;
	}

	public HkGroupUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkGroupUser o = new HkGroupUser();
		o.setSysId(rs.getLong("sysid"));
		o.setGroupId(rs.getInt("groupid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
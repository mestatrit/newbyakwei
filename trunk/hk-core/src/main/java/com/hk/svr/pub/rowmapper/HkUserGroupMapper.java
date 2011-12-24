package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkUserGroup;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkUserGroupMapper extends HkRowMapper<HkUserGroup> {
	@Override
	public Class<HkUserGroup> getMapperClass() {
		return HkUserGroup.class;
	}

	public HkUserGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkUserGroup o = new HkUserGroup();
		o.setSysId(rs.getLong("sysid"));
		o.setGroupId(rs.getInt("groupid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
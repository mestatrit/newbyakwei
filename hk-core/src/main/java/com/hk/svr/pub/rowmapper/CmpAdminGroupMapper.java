package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpAdminGroup;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpAdminGroupMapper extends HkRowMapper<CmpAdminGroup> {
	@Override
	public Class<CmpAdminGroup> getMapperClass() {
		return CmpAdminGroup.class;
	}

	public CmpAdminGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpAdminGroup o = new CmpAdminGroup();
		o.setGroupId(rs.getLong("groupid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
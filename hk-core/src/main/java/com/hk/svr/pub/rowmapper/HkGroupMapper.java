package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkGroup;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkGroupMapper extends HkRowMapper<HkGroup> {
	@Override
	public Class<HkGroup> getMapperClass() {
		return HkGroup.class;
	}

	public HkGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkGroup o = new HkGroup();
		o.setGroupId(rs.getInt("groupid"));
		o.setName(rs.getString("name"));
		o.setUcount(rs.getInt("ucount"));
		return o;
	}
}
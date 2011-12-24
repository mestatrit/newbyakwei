package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpAdGroup;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpAdGroupMapper extends HkRowMapper<CmpAdGroup> {

	@Override
	public Class<CmpAdGroup> getMapperClass() {
		return CmpAdGroup.class;
	}

	public CmpAdGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpAdGroup o = new CmpAdGroup();
		o.setGroupId(rs.getLong("groupid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
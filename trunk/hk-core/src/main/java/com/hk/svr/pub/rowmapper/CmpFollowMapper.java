package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpFollow;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpFollowMapper extends HkRowMapper<CmpFollow> {
	@Override
	public Class<CmpFollow> getMapperClass() {
		return CmpFollow.class;
	}

	public CmpFollow mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpFollow o = new CmpFollow();
		o.setSysId(rs.getLong("sysid"));
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
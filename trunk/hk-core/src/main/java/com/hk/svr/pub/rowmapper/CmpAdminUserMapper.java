package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpAdminUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpAdminUserMapper extends HkRowMapper<CmpAdminUser> {

	@Override
	public Class<CmpAdminUser> getMapperClass() {
		return CmpAdminUser.class;
	}

	public CmpAdminUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpAdminUser o = new CmpAdminUser();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActorRole;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActorRoleMapper extends HkRowMapper<CmpActorRole> {

	@Override
	public Class<CmpActorRole> getMapperClass() {
		return CmpActorRole.class;
	}

	public CmpActorRole mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActorRole o = new CmpActorRole();
		o.setRoleId(rs.getLong("roleid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
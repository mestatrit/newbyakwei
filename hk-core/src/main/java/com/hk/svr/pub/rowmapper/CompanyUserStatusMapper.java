package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CompanyUserStatus;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyUserStatusMapper extends HkRowMapper<CompanyUserStatus> {

	@Override
	public Class<CompanyUserStatus> getMapperClass() {
		return CompanyUserStatus.class;
	}

	public CompanyUserStatus mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CompanyUserStatus o = new CompanyUserStatus();
		o.setSysId(rs.getLong("sysid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setUserStatus(rs.getByte("userstatus"));
		o.setDoneStatus(rs.getByte("donestatus"));
		return o;
	}
}
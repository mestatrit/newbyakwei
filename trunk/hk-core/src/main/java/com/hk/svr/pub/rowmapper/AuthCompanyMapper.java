package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.AuthCompany;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class AuthCompanyMapper extends HkRowMapper<AuthCompany> {

	@Override
	public Class<AuthCompany> getMapperClass() {
		return AuthCompany.class;
	}

	public AuthCompany mapRow(ResultSet rs, int rowNum) throws SQLException {
		AuthCompany o = new AuthCompany();
		o.setSysId(rs.getLong("sysid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setContent(rs.getString("content"));
		o.setMainStatus(rs.getByte("mainstatus"));
		o.setName(rs.getString("name"));
		o.setTel(rs.getString("tel"));
		o.setUsername(rs.getString("username"));
		return o;
	}
}
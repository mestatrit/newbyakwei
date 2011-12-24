package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.yuming.Domain;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class DomainMapper extends HkRowMapper<Domain> {

	@Override
	public Class<Domain> getMapperClass() {
		return Domain.class;
	}

	@Override
	public Domain mapRow(ResultSet rs, int rowNum) throws SQLException {
		Domain domain = new Domain();
		domain.setDomainid(rs.getInt("domainid"));
		domain.setName(rs.getString("name"));
		domain.setDescr(rs.getString("descr"));
		return domain;
	}
}

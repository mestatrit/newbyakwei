package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Domain;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_DomainMapper extends HkRowMapper<Tb_Domain> {

	@Override
	public Class<Tb_Domain> getMapperClass() {
		return Tb_Domain.class;
	}

	public Tb_Domain mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Domain o = new Tb_Domain();
		o.setDomainid(rs.getInt("domainid"));
		o.setName(rs.getString("name"));
		o.setShow_level(rs.getByte("show_level"));
		return o;
	}
}
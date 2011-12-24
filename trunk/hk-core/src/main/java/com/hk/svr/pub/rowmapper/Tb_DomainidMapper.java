package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Domainid;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_DomainidMapper extends HkRowMapper<Tb_Domainid> {

	@Override
	public Class<Tb_Domainid> getMapperClass() {
		return Tb_Domainid.class;
	}

	public Tb_Domainid mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Domainid o = new Tb_Domainid();
		o.setDomainid(rs.getInt("domainid"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
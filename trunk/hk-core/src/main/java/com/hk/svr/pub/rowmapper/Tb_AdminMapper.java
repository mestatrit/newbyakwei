package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Admin;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.dao.rowmapper.HkRowMapper;

@Table(name = "tb_admin")
public class Tb_AdminMapper extends HkRowMapper<Tb_Admin> {

	@Override
	public Class<Tb_Admin> getMapperClass() {
		return Tb_Admin.class;
	}

	@Override
	public Tb_Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Admin o = new Tb_Admin();
		o.setOid(rs.getLong("oid"));
		o.setUserid(rs.getLong("userid"));
		return o;
	}
}
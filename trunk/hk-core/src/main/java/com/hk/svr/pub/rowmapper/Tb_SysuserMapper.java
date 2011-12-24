package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Sysuser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_SysuserMapper extends HkRowMapper<Tb_Sysuser> {

	@Override
	public Class<Tb_Sysuser> getMapperClass() {
		return Tb_Sysuser.class;
	}

	@Override
	public Tb_Sysuser mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Sysuser o = new Tb_Sysuser();
		o.setUserid(rs.getLong("userid"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
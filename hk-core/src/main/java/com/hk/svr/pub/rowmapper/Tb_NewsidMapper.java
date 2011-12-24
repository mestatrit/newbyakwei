package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Newsid;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_NewsidMapper extends HkRowMapper<Tb_Newsid> {

	@Override
	public Class<Tb_Newsid> getMapperClass() {
		return Tb_Newsid.class;
	}

	public Tb_Newsid mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Newsid o = new Tb_Newsid();
		o.setNid(rs.getLong("nid"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
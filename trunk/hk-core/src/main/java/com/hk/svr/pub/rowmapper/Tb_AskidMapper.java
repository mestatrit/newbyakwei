package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Askid;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_AskidMapper extends HkRowMapper<Tb_Askid> {

	@Override
	public Class<Tb_Askid> getMapperClass() {
		return Tb_Askid.class;
	}

	public Tb_Askid mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Askid o = new Tb_Askid();
		o.setAid(rs.getLong("aid"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
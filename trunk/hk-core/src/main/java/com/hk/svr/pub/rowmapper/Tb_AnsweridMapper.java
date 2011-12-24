package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Answerid;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_AnsweridMapper extends HkRowMapper<Tb_Answerid> {

	@Override
	public Class<Tb_Answerid> getMapperClass() {
		return Tb_Answerid.class;
	}

	public Tb_Answerid mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Answerid o = new Tb_Answerid();
		o.setAnsid(rs.getLong("ansid"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
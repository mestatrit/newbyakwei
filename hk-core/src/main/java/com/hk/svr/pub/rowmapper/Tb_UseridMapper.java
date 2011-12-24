package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Userid;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_UseridMapper extends HkRowMapper<Tb_Userid> {

	@Override
	public Class<Tb_Userid> getMapperClass() {
		return Tb_Userid.class;
	}

	public Tb_Userid mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Userid o = new Tb_Userid();
		o.setUserid(rs.getLong("userid"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Sina_User;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Sina_UserMapper extends HkRowMapper<Tb_Sina_User> {

	@Override
	public Class<Tb_Sina_User> getMapperClass() {
		return Tb_Sina_User.class;
	}

	public Tb_Sina_User mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Sina_User o = new Tb_Sina_User();
		o.setUid(rs.getLong("uid"));
		o.setUserid(rs.getLong("userid"));
		return o;
	}
}
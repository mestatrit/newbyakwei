package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_User_Ask;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_User_AskMapper extends HkRowMapper<Tb_User_Ask> {

	@Override
	public Class<Tb_User_Ask> getMapperClass() {
		return Tb_User_Ask.class;
	}

	@Override
	public Tb_User_Ask mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_User_Ask o = new Tb_User_Ask();
		o.setAid(rs.getLong("aid"));
		o.setUserid(rs.getLong("userid"));
		o.setAskflg(rs.getByte("askflg"));
		o.setOid(rs.getLong("oid"));
		return o;
	}
}
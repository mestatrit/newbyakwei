package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Ask_Index;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Ask_IndexMapper extends HkRowMapper<Tb_Ask_Index> {

	@Override
	public Class<Tb_Ask_Index> getMapperClass() {
		return Tb_Ask_Index.class;
	}

	@Override
	public Tb_Ask_Index mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Ask_Index o = new Tb_Ask_Index();
		o.setOid(rs.getLong("oid"));
		o.setAid(rs.getLong("aid"));
		o.setFlg(rs.getByte("flg"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
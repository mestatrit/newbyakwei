package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Itemid;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_ItemidMapper extends HkRowMapper<Tb_Itemid> {

	@Override
	public Class<Tb_Itemid> getMapperClass() {
		return Tb_Itemid.class;
	}

	public Tb_Itemid mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Itemid o = new Tb_Itemid();
		o.setItemid(rs.getLong("itemid"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
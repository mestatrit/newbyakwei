package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Item_Cmtid;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Item_CmtidMapper extends HkRowMapper<Tb_Item_Cmtid> {

	@Override
	public Class<Tb_Item_Cmtid> getMapperClass() {
		return Tb_Item_Cmtid.class;
	}

	public Tb_Item_Cmtid mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Item_Cmtid o = new Tb_Item_Cmtid();
		o.setCmtid(rs.getLong("cmtid"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
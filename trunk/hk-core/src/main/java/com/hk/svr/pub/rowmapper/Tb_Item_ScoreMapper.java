package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Item_Score;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Item_ScoreMapper extends HkRowMapper<Tb_Item_Score> {

	@Override
	public Class<Tb_Item_Score> getMapperClass() {
		return Tb_Item_Score.class;
	}

	public Tb_Item_Score mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Item_Score o = new Tb_Item_Score();
		o.setOid(rs.getLong("oid"));
		o.setItemid(rs.getLong("itemid"));
		o.setUserid(rs.getLong("userid"));
		o.setScore(rs.getInt("score"));
		return o;
	}
}
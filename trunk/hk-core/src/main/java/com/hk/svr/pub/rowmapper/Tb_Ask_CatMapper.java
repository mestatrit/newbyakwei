package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Ask_Cat;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Ask_CatMapper extends HkRowMapper<Tb_Ask_Cat> {

	@Override
	public Class<Tb_Ask_Cat> getMapperClass() {
		return Tb_Ask_Cat.class;
	}

	@Override
	public Tb_Ask_Cat mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Ask_Cat o = new Tb_Ask_Cat();
		o.setCid(rs.getLong("cid"));
		o.setParent_cid(rs.getLong("parent_cid"));
		o.setName(rs.getString("name"));
		o.setOrder_num(rs.getInt("order_num"));
		o.setParentflg(rs.getByte("parentflg"));
		return o;
	}
}
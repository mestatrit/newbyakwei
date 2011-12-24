package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Shop_Cat;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Shop_CatMapper extends HkRowMapper<Tb_Shop_Cat> {

	@Override
	public Class<Tb_Shop_Cat> getMapperClass() {
		return Tb_Shop_Cat.class;
	}

	public Tb_Shop_Cat mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Shop_Cat o = new Tb_Shop_Cat();
		o.setCid(rs.getLong("cid"));
		o.setName(rs.getString("name"));
		o.setParent_cid(rs.getLong("parent_cid"));
		o.setParentflg(rs.getByte("parentflg"));
		return o;
	}
}
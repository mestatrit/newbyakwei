package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Domain_Item;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Domain_ItemMapper extends HkRowMapper<Tb_Domain_Item> {

	@Override
	public Class<Tb_Domain_Item> getMapperClass() {
		return Tb_Domain_Item.class;
	}

	public Tb_Domain_Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Domain_Item o = new Tb_Domain_Item();
		o.setOid(rs.getLong("oid"));
		o.setDomainid(rs.getInt("domainid"));
		o.setItemid(rs.getLong("itemid"));
		o.setHkscore(rs.getInt("hkscore"));
		o.setHuo_status(rs.getInt("huo_status"));
		o.setVolume(rs.getLong("volume"));
		return o;
	}
}
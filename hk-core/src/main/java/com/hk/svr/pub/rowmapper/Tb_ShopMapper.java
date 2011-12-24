package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Shop;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_ShopMapper extends HkRowMapper<Tb_Shop> {

	@Override
	public Class<Tb_Shop> getMapperClass() {
		return Tb_Shop.class;
	}

	public Tb_Shop mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Shop o = new Tb_Shop();
		o.setSid(rs.getLong("sid"));
		o.setCid(rs.getLong("cid"));
		o.setTb_sid(rs.getLong("tb_sid"));
		o.setTitle(rs.getString("title"));
		o.setNick(rs.getString("nick"));
		o.setIntro(rs.getString("intro"));
		o.setPic_path(rs.getString("pic_path"));
		o.setCreated(rs.getTimestamp("created"));
		o.setModified(rs.getTimestamp("modified"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_News;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_NewsMapper extends HkRowMapper<Tb_News> {

	@Override
	public Class<Tb_News> getMapperClass() {
		return Tb_News.class;
	}

	public Tb_News mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_News o = new Tb_News();
		o.setNid(rs.getLong("nid"));
		o.setUserid(rs.getLong("userid"));
		o.setOid(rs.getLong("oid"));
		o.setNtype(rs.getInt("ntype"));
		o.setData(rs.getString("data"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
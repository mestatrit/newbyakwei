package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Ask;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_AskMapper extends HkRowMapper<Tb_Ask> {

	@Override
	public Class<Tb_Ask> getMapperClass() {
		return Tb_Ask.class;
	}

	public Tb_Ask mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Ask o = new Tb_Ask();
		o.setAid(rs.getLong("aid"));
		o.setUserid(rs.getLong("userid"));
		o.setAnsid(rs.getLong("ansid"));
		o.setTitle(rs.getString("title"));
		o.setContent(rs.getString("content"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		o.setAnswer_num(rs.getInt("answer_num"));
		o.setResolve_status(rs.getByte("resolve_status"));
		o.setCid(rs.getLong("cid"));
		o.setParent_cid(rs.getLong("parent_cid"));
		return o;
	}
}
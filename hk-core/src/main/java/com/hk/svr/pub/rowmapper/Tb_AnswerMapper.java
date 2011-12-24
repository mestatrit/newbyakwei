package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Answer;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_AnswerMapper extends HkRowMapper<Tb_Answer> {

	@Override
	public Class<Tb_Answer> getMapperClass() {
		return Tb_Answer.class;
	}

	public Tb_Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Answer o = new Tb_Answer();
		o.setAnsid(rs.getLong("ansid"));
		o.setUserid(rs.getLong("userid"));
		o.setAid(rs.getLong("aid"));
		o.setContent(rs.getString("content"));
		o.setResolve_content(rs.getString("resolve_content"));
		o.setSupport_num(rs.getInt("support_num"));
		o.setDiscmd_num(rs.getInt("discmd_num"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Notice;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_NoticeMapper extends HkRowMapper<Tb_Notice> {

	@Override
	public Class<Tb_Notice> getMapperClass() {
		return Tb_Notice.class;
	}

	public Tb_Notice mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Notice o = new Tb_Notice();
		o.setNoticeid(rs.getLong("noticeid"));
		o.setUserid(rs.getLong("userid"));
		o.setNtype(rs.getInt("ntype"));
		o.setData(rs.getString("data"));
		o.setReadflg(rs.getByte("readflg"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		o.setRef_oid(rs.getLong("ref_oid"));
		return o;
	}
}
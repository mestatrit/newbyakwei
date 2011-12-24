package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Item_CmtMapper extends HkRowMapper<Tb_Item_Cmt> {

	@Override
	public Class<Tb_Item_Cmt> getMapperClass() {
		return Tb_Item_Cmt.class;
	}

	public Tb_Item_Cmt mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Item_Cmt o = new Tb_Item_Cmt();
		o.setCmtid(rs.getLong("cmtid"));
		o.setItemid(rs.getLong("itemid"));
		o.setUserid(rs.getLong("userid"));
		o.setContent(rs.getString("content"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		o.setScore(rs.getInt("score"));
		o.setReply_count(rs.getInt("reply_count"));
		o.setSid(rs.getLong("sid"));
		return o;
	}
}
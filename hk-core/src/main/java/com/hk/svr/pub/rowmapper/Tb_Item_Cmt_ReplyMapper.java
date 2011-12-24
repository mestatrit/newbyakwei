package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Item_Cmt_Reply;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Item_Cmt_ReplyMapper extends HkRowMapper<Tb_Item_Cmt_Reply> {

	@Override
	public Class<Tb_Item_Cmt_Reply> getMapperClass() {
		return Tb_Item_Cmt_Reply.class;
	}

	public Tb_Item_Cmt_Reply mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		Tb_Item_Cmt_Reply o = new Tb_Item_Cmt_Reply();
		o.setReplyid(rs.getLong("replyid"));
		o.setItemid(rs.getLong("itemid"));
		o.setUserid(rs.getLong("userid"));
		o.setCmtid(rs.getLong("cmtid"));
		o.setContent(rs.getString("content"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
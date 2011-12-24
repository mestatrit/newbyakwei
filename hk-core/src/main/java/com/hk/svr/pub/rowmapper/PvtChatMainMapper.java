package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.PvtChatMain;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class PvtChatMainMapper extends HkRowMapper<PvtChatMain> {
	@Override
	public Class<PvtChatMain> getMapperClass() {
		return PvtChatMain.class;
	}

	public PvtChatMain mapRow(ResultSet rs, int rowNum) throws SQLException {
		PvtChatMain o = new PvtChatMain();
		o.setMainId(rs.getLong("mainid"));
		o.setUserId(rs.getLong("userid"));
		o.setUser2Id(rs.getLong("user2id"));
		o.setLast3msg(rs.getString("last3msg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setReadflg(rs.getByte("readflg"));
		o.setNoReadCount(rs.getInt("noreadcount"));
		return o;
	}
}
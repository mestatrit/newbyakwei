package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.PvtChat;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class PvtChatMapper extends HkRowMapper<PvtChat> {
	@Override
	public Class<PvtChat> getMapperClass() {
		return PvtChat.class;
	}

	public PvtChat mapRow(ResultSet rs, int rowNum) throws SQLException {
		PvtChat o = new PvtChat();
		o.setChatId(rs.getLong("chatid"));
		o.setUserId(rs.getLong("userid"));
		o.setSenderId(rs.getLong("senderid"));
		o.setMsg(rs.getString("msg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setMainId(rs.getLong("mainid"));
		o.setSmsflg(rs.getByte("smsflg"));
		o.setSmsmsgId(rs.getLong("smsmsgid"));
		return o;
	}
}
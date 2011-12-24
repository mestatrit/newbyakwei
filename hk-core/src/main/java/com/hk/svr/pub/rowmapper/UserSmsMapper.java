package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserSms;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserSmsMapper extends HkRowMapper<UserSms> {
	@Override
	public Class<UserSms> getMapperClass() {
		return UserSms.class;
	}

	public UserSms mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserSms o = new UserSms();
		o.setMsgId(rs.getLong("msgid"));
		o.setSenderId(rs.getLong("senderid"));
		o.setContent(rs.getString("content"));
		o.setPort(rs.getString("port"));
		o.setSmsstate(rs.getByte("smsstate"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setReceiverId(rs.getLong("receiverid"));
		o.setStatemsg(rs.getString("statemsg"));
		o.setSmsflg(rs.getByte("smsflg"));
		return o;
	}

	public static void main(String[] args) {
		System.out.println(System.nanoTime());
	}
}
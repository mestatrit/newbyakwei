package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserBatchSms;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserBatchSmsMapper extends HkRowMapper<UserBatchSms> {
	@Override
	public Class<UserBatchSms> getMapperClass() {
		return UserBatchSms.class;
	}

	public UserBatchSms mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserBatchSms o = new UserBatchSms();
		o.setSysId(rs.getLong("sysid"));
		o.setMsgId(rs.getLong("msgid"));
		o.setReceiverId(rs.getLong("receiverid"));
		return o;
	}
}
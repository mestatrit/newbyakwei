package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserLabaReply;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserLabaReplyMapper extends HkRowMapper<UserLabaReply> {
	@Override
	public Class<UserLabaReply> getMapperClass() {
		return UserLabaReply.class;
	}

	public UserLabaReply mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserLabaReply o = new UserLabaReply();
		o.setUserId(rs.getLong("userid"));
		o.setLabaId(rs.getLong("labaid"));
		return o;
	}
}
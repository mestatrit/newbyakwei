package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserTool;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserToolMapper extends HkRowMapper<UserTool> {
	@Override
	public Class<UserTool> getMapperClass() {
		return UserTool.class;
	}

	public UserTool mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserTool o = UserTool.createEmpty();
		o.setUserId(rs.getLong("userid"));
		o.setGroundCount(rs.getInt("groundcount"));
		o.setLabartflg(rs.getByte("labartflg"));
		o.setShowReply(rs.getByte("showreply"));
		o.setInviteCount(rs.getInt("invitecount"));
		return o;
	}
}
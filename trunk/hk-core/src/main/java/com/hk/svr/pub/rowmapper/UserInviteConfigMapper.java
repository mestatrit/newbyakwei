package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserInviteConfig;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserInviteConfigMapper extends HkRowMapper<UserInviteConfig> {

	@Override
	public Class<UserInviteConfig> getMapperClass() {
		return UserInviteConfig.class;
	}

	public UserInviteConfig mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		UserInviteConfig o = new UserInviteConfig();
		o.setUserId(rs.getLong("userid"));
		o.setInviteNum(rs.getInt("invitenum"));
		o.setBatchNum(rs.getInt("batchnum"));
		return o;
	}
}
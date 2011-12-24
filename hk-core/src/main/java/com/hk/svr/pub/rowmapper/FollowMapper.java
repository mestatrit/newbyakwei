package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Follow;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class FollowMapper extends HkRowMapper<Follow> {
	public Follow mapRow(ResultSet rs, int rowNum) throws SQLException {
		Follow f = new Follow();
		f.setSysId(rs.getLong("sysid"));
		f.setUserId(rs.getLong("userid"));
		f.setFriendId(rs.getLong("friendid"));
		f.setBothFollow(rs.getByte("bothfollow"));
		return f;
	}

	@Override
	public Class<Follow> getMapperClass() {
		return Follow.class;
	}
}
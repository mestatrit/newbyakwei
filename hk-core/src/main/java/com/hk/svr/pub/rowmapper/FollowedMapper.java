package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Followed;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class FollowedMapper extends HkRowMapper<Followed> {
	public Followed mapRow(ResultSet rs, int rowNum) throws SQLException {
		Followed f = new Followed();
		f.setSysId(rs.getLong("sysid"));
		f.setUserId(rs.getLong("userid"));
		f.setFollowingUserId(rs.getLong("followinguserid"));
		f.setBothFollow(rs.getByte("bothfollow"));
		return f;
	}

	@Override
	public Class<Followed> getMapperClass() {
		return Followed.class;
	}
}
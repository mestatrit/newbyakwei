package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.DefFollowUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class DefFollowUserMapper extends HkRowMapper<DefFollowUser> {
	@Override
	public Class<DefFollowUser> getMapperClass() {
		return DefFollowUser.class;
	}

	public DefFollowUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		DefFollowUser o = new DefFollowUser();
		o.setUserId(rs.getLong("userid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
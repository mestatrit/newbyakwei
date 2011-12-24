package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.User;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserMapper extends HkRowMapper<User> {
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User o = new User();
		o.setUserId(rs.getLong("userid"));
		o.setNickName(rs.getString("nickname"));
		o.setHeadflg(rs.getByte("headflg"));
		o.setHeadPath(rs.getString("headpath"));
		o.setDomain(rs.getString("domain"));
		o.setFriendCount(rs.getInt("friendcount"));
		o.setFansCount(rs.getInt("fanscount"));
		o.setCityId(rs.getInt("cityid"));
		o.setSex(rs.getByte("sex"));
		o.setPcityId(rs.getInt("pcityid"));
		return o;
	}

	@Override
	public Class<User> getMapperClass() {
		return User.class;
	}
}
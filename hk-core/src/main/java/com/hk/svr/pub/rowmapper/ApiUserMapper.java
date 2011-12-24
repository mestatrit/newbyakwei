package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.ApiUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ApiUserMapper extends HkRowMapper<ApiUser> {
	@Override
	public Class<ApiUser> getMapperClass() {
		return ApiUser.class;
	}

	public ApiUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		ApiUser o = new ApiUser();
		o.setApiUserId(rs.getInt("apiuserid"));
		o.setUserKey(rs.getString("userkey"));
		o.setName(rs.getString("name"));
		o.setLockflg(rs.getByte("lockflg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setUrl(rs.getString("url"));
		o.setUserId(rs.getLong("userid"));
		o.setCheckflg(rs.getByte("checkflg"));
		return o;
	}
}
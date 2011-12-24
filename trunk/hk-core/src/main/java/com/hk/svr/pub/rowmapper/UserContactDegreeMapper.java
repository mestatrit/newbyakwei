package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserContactDegree;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserContactDegreeMapper extends HkRowMapper<UserContactDegree> {
	@Override
	public Class<UserContactDegree> getMapperClass() {
		return UserContactDegree.class;
	}

	public UserContactDegree mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		UserContactDegree o = new UserContactDegree();
		o.setUserId(rs.getLong("userid"));
		o.setContactUserId(rs.getLong("contactuserid"));
		o.setDegree(rs.getInt("degree"));
		return o;
	}
}
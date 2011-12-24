package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserLabaDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserLabaDelMapper extends HkRowMapper<UserLabaDel> {
	@Override
	public Class<UserLabaDel> getMapperClass() {
		return UserLabaDel.class;
	}

	public UserLabaDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserLabaDel o = new UserLabaDel();
		o.setLabaId(rs.getLong("labaid"));
		o.setUserId(rs.getLong("userid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}
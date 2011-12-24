package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserLabaReplyDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserLabaReplyDelMapper extends HkRowMapper<UserLabaReplyDel> {
	@Override
	public Class<UserLabaReplyDel> getMapperClass() {
		return UserLabaReplyDel.class;
	}

	public UserLabaReplyDel mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		UserLabaReplyDel o = new UserLabaReplyDel();
		o.setUserId(rs.getLong("userid"));
		o.setLabaId(rs.getLong("labaid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}
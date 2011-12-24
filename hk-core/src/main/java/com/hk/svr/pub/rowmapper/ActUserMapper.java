package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.ActUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ActUserMapper extends HkRowMapper<ActUser> {
	@Override
	public Class<ActUser> getMapperClass() {
		return ActUser.class;
	}

	public ActUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		ActUser o = new ActUser();
		o.setSysId(rs.getLong("sysid"));
		o.setActId(rs.getLong("actid"));
		o.setUserId(rs.getLong("userid"));
		o.setCheckflg(rs.getByte("checkflg"));
		return o;
	}
}
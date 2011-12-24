package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.ProUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ProUserMapper extends HkRowMapper<ProUser> {
	@Override
	public Class<ProUser> getMapperClass() {
		return ProUser.class;
	}

	public ProUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProUser o = new ProUser();
		o.setOid(rs.getLong("oid"));
		o.setNickName(rs.getString("nickname"));
		o.setInput(rs.getString("input"));
		o.setIntro(rs.getString("intro"));
		o.setUptime(rs.getTimestamp("uptime"));
		o.setCreaterId(rs.getLong("createrId"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
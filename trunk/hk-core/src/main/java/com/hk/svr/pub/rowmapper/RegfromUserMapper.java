package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.RegfromUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class RegfromUserMapper extends HkRowMapper<RegfromUser> {
	@Override
	public Class<RegfromUser> getMapperClass() {
		return RegfromUser.class;
	}

	public RegfromUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		RegfromUser o = new RegfromUser();
		o.setUserId(rs.getLong("userid"));
		o.setRegfrom(rs.getInt("regfrom"));
		o.setFromId(rs.getLong("fromid"));
		return o;
	}
}
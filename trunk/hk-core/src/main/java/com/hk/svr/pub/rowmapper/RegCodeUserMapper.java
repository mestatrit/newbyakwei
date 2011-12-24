package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.RegCodeUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class RegCodeUserMapper extends HkRowMapper<RegCodeUser> {
	@Override
	public Class<RegCodeUser> getMapperClass() {
		return RegCodeUser.class;
	}

	public RegCodeUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		RegCodeUser o = new RegCodeUser();
		o.setCodeId(rs.getLong("codeid"));
		o.setUserId(rs.getLong("userid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
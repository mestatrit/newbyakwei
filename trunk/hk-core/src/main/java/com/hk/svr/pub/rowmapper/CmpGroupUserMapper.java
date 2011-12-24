package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpGroupUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpGroupUserMapper extends HkRowMapper<CmpGroupUser> {
	@Override
	public Class<CmpGroupUser> getMapperClass() {
		return CmpGroupUser.class;
	}

	public CmpGroupUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpGroupUser o = new CmpGroupUser();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setCmpgroupId(rs.getLong("cmpgroupid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
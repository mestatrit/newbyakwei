package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.RefLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class RefLabaMapper extends HkRowMapper<RefLaba> {
	@Override
	public Class<RefLaba> getMapperClass() {
		return RefLaba.class;
	}

	public RefLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		RefLaba o = new RefLaba();
		o.setLabaId(rs.getLong("labaid"));
		o.setRefUserId(rs.getLong("refuserid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
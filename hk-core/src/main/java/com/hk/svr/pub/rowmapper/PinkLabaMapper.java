package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.PinkLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class PinkLabaMapper extends HkRowMapper<PinkLaba> {
	@Override
	public Class<PinkLaba> getMapperClass() {
		return PinkLaba.class;
	}

	public PinkLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		PinkLaba o = new PinkLaba();
		o.setLabaId(rs.getLong("labaid"));
		o.setPinkUserId(rs.getLong("pinkuserid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.RespLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class RespLabaMapper extends HkRowMapper<RespLaba> {
	@Override
	public Class<RespLaba> getMapperClass() {
		return RespLaba.class;
	}

	public RespLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		RespLaba o = new RespLaba();
		o.setLabaId(rs.getLong("labaid"));
		o.setRespcount(rs.getInt("respcount"));
		o.setUptime(rs.getTimestamp("uptime"));
		o.setHot(rs.getInt("hot"));
		return o;
	}
}
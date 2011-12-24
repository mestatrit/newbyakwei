package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkbLog;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkbLogMapper extends HkRowMapper<HkbLog> {
	@Override
	public Class<HkbLog> getMapperClass() {
		return HkbLog.class;
	}

	public HkbLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkbLog o = new HkbLog();
		o.setLogId(rs.getLong("logid"));
		o.setUserId(rs.getLong("userid"));
		o.setHkbtype(rs.getInt("hkbtype"));
		o.setAddcount(rs.getInt("addcount"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setObjId(rs.getLong("objid"));
		return o;
	}
}
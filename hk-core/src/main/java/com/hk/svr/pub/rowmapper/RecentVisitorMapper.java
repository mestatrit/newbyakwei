package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.RecentVisitor;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class RecentVisitorMapper extends HkRowMapper<RecentVisitor> {
	@Override
	public Class<RecentVisitor> getMapperClass() {
		return RecentVisitor.class;
	}

	public RecentVisitor mapRow(ResultSet rs, int rowNum) throws SQLException {
		RecentVisitor o = new RecentVisitor();
		o.setUptime(rs.getTimestamp("uptime"));
		o.setUserId(rs.getLong("userid"));
		o.setVisitorId(rs.getLong("visitorid"));
		o.setOid(rs.getLong("oid"));
		return o;
	}
}
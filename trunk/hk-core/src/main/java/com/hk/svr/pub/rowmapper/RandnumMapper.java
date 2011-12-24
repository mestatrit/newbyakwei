package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Randnum;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class RandnumMapper extends HkRowMapper<Randnum> {
	@Override
	public Class<Randnum> getMapperClass() {
		return Randnum.class;
	}

	public Randnum mapRow(ResultSet rs, int rowNum) throws SQLException {
		Randnum o = new Randnum();
		o.setSysId(rs.getInt("sysid"));
		o.setRandvalue(rs.getInt("randvalue"));
		o.setUserId(rs.getLong("userid"));
		o.setUtime(rs.getTimestamp("utime"));
		o.setInuse(rs.getByte("inuse"));
		return o;
	}
}
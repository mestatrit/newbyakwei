package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActorSpTime;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActorSpTimeMapper extends HkRowMapper<CmpActorSpTime> {

	@Override
	public Class<CmpActorSpTime> getMapperClass() {
		return CmpActorSpTime.class;
	}

	public CmpActorSpTime mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActorSpTime o = new CmpActorSpTime();
		o.setOid(rs.getLong("oid"));
		o.setActorId(rs.getLong("actorid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setBeginTime(rs.getTimestamp("begintime"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
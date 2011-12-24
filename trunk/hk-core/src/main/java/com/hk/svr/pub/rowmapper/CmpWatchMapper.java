package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpWatch;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpWatchMapper extends HkRowMapper<CmpWatch> {
	@Override
	public Class<CmpWatch> getMapperClass() {
		return CmpWatch.class;
	}

	public CmpWatch mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpWatch o = new CmpWatch();
		o.setSysId(rs.getLong("sysid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setDuty(rs.getByte("duty"));
		return o;
	}
}
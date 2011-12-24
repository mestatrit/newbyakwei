package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpHkbLog;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpHkbLogMapper extends HkRowMapper<CmpHkbLog> {
	@Override
	public Class<CmpHkbLog> getMapperClass() {
		return CmpHkbLog.class;
	}

	public CmpHkbLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpHkbLog o = new CmpHkbLog();
		o.setLogId(rs.getLong("logid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setAddcount(rs.getInt("addcount"));
		o.setUserId(rs.getLong("userid"));
		o.setHkbtype(rs.getInt("hktype"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
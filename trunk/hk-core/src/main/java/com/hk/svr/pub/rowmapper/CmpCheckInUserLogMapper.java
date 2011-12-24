package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpCheckInUserLog;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpCheckInUserLogMapper extends HkRowMapper<CmpCheckInUserLog> {
	@Override
	public Class<CmpCheckInUserLog> getMapperClass() {
		return CmpCheckInUserLog.class;
	}

	public CmpCheckInUserLog mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpCheckInUserLog o = new CmpCheckInUserLog();
		o.setLogId(rs.getLong("logid"));
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setNightflg(rs.getByte("nightflg"));
		o.setSex(rs.getByte("sex"));
		o.setEffectflg(rs.getByte("effectflg"));
		o.setParentId(rs.getInt("parentid"));
		o.setKindId(rs.getInt("kindid"));
		o.setGroupId(rs.getLong("groupid"));
		o.setPcityId(rs.getInt("pcityid"));
		return o;
	}
}
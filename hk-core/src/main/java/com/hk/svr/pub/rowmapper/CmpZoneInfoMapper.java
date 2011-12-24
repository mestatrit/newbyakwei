package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpZoneInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpZoneInfoMapper extends HkRowMapper<CmpZoneInfo> {
	@Override
	public Class<CmpZoneInfo> getMapperClass() {
		return CmpZoneInfo.class;
	}

	public CmpZoneInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpZoneInfo o = new CmpZoneInfo();
		o.setCmpcount(rs.getInt("cmpcount"));
		o.setSysId(rs.getInt("sysid"));
		o.setPcityId(rs.getInt("pcityid"));
		return o;
	}
}
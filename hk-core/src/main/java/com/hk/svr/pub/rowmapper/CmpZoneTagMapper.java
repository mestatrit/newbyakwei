package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpZoneTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpZoneTagMapper extends HkRowMapper<CmpZoneTag> {
	@Override
	public Class<CmpZoneTag> getMapperClass() {
		return CmpZoneTag.class;
	}

	public CmpZoneTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpZoneTag o = new CmpZoneTag();
		o.setOid(rs.getLong("oid"));
		o.setTagId(rs.getLong("tagid"));
		o.setCityId(rs.getInt("cityid"));
		o.setKindId(rs.getInt("kindid"));
		o.setCmpCount(rs.getInt("cmpcount"));
		return o;
	}
}
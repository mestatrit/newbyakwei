package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUnionLink;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUnionLinkMapper extends HkRowMapper<CmpUnionLink> {
	@Override
	public Class<CmpUnionLink> getMapperClass() {
		return CmpUnionLink.class;
	}

	public CmpUnionLink mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpUnionLink o = new CmpUnionLink();
		o.setLinkId(rs.getLong("linkid"));
		o.setUid(rs.getLong("uid"));
		o.setTitle(rs.getString("title"));
		o.setUrl(rs.getString("url"));
		return o;
	}
}
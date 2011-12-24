package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpTagRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpTagRefMapper extends HkRowMapper<CmpTagRef> {
	@Override
	public Class<CmpTagRef> getMapperClass() {
		return CmpTagRef.class;
	}

	public CmpTagRef mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpTagRef o = new CmpTagRef();
		o.setCompanyId(rs.getLong("companyid"));
		o.setTagId(rs.getLong("tagid"));
		o.setUserId(rs.getLong("userid"));
		o.setName(rs.getString("name"));
		o.setPcityId(rs.getInt("pcityid"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductTagRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductTagRefMapper extends HkRowMapper<CmpProductTagRef> {
	@Override
	public Class<CmpProductTagRef> getMapperClass() {
		return CmpProductTagRef.class;
	}

	public CmpProductTagRef mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpProductTagRef o = new CmpProductTagRef();
		o.setCompanyId(rs.getLong("companyid"));
		o.setTagId(rs.getLong("tagid"));
		return o;
	}
}
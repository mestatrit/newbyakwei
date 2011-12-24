package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpTablePhotoSet;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpTablePhotoSetMapper extends HkRowMapper<CmpTablePhotoSet> {
	@Override
	public Class<CmpTablePhotoSet> getMapperClass() {
		return CmpTablePhotoSet.class;
	}

	public CmpTablePhotoSet mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpTablePhotoSet o = new CmpTablePhotoSet();
		o.setCompanyId(rs.getLong("companyid"));
		o.setSetId(rs.getLong("setid"));
		o.setTitle(rs.getString("title"));
		o.setIntro(rs.getString("intro"));
		o.setPath(rs.getString("path"));
		return o;
	}
}
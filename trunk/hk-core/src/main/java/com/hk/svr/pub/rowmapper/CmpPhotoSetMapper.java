package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpPhotoSet;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpPhotoSetMapper extends HkRowMapper<CmpPhotoSet> {

	@Override
	public Class<CmpPhotoSet> getMapperClass() {
		return CmpPhotoSet.class;
	}

	public CmpPhotoSet mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpPhotoSet o = new CmpPhotoSet();
		o.setSetId(rs.getLong("setid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setPicPath(rs.getString("picpath"));
		return o;
	}
}
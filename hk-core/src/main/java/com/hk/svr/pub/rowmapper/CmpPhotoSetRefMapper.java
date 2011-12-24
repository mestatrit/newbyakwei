package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpPhotoSetRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpPhotoSetRefMapper extends HkRowMapper<CmpPhotoSetRef> {

	@Override
	public Class<CmpPhotoSetRef> getMapperClass() {
		return CmpPhotoSetRef.class;
	}

	public CmpPhotoSetRef mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpPhotoSetRef o = new CmpPhotoSetRef();
		o.setOid(rs.getLong("oid"));
		o.setSetId(rs.getLong("setid"));
		o.setPhotoId(rs.getLong("photoid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
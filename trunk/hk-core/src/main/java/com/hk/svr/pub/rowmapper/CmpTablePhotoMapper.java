package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpTablePhoto;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpTablePhotoMapper extends HkRowMapper<CmpTablePhoto> {
	@Override
	public Class<CmpTablePhoto> getMapperClass() {
		return CmpTablePhoto.class;
	}

	public CmpTablePhoto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpTablePhoto o = new CmpTablePhoto();
		o.setOid(rs.getLong("oid"));
		o.setPhotoId(rs.getLong("photoid"));
		o.setSetId(rs.getLong("setid"));
		o.setPath(rs.getString("path"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
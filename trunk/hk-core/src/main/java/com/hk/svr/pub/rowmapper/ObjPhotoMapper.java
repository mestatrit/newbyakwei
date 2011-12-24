package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.ObjPhoto;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ObjPhotoMapper extends HkRowMapper<ObjPhoto> {

	@Override
	public Class<ObjPhoto> getMapperClass() {
		return ObjPhoto.class;
	}

	public ObjPhoto mapRow(ResultSet rs, int rowNum) throws SQLException {
		ObjPhoto o = new ObjPhoto();
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setPhotoId(rs.getLong("photoid"));
		o.setPath(rs.getString("path"));
		return o;
	}
}
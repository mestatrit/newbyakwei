package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpProductPhoto;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductPhotoMapper extends HkRowMapper<CmpProductPhoto> {
	@Override
	public Class<CmpProductPhoto> getMapperClass() {
		return CmpProductPhoto.class;
	}

	public CmpProductPhoto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpProductPhoto o = new CmpProductPhoto();
		o.setOid(rs.getLong("oid"));
		o.setPhotoId(rs.getLong("photoid"));
		o.setProductId(rs.getInt("productid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setPath(rs.getString("path"));
//		o.setName(rs.getString("name"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
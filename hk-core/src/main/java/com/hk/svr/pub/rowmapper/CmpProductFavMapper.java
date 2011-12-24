package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductFav;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductFavMapper extends HkRowMapper<CmpProductFav> {
	@Override
	public Class<CmpProductFav> getMapperClass() {
		return CmpProductFav.class;
	}

	public CmpProductFav mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpProductFav o = new CmpProductFav();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setProductId(rs.getLong("productid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
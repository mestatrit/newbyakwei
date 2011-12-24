package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserProduct;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserProductMapper extends HkRowMapper<UserProduct> {
	@Override
	public Class<UserProduct> getMapperClass() {
		return UserProduct.class;
	}

	public UserProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserProduct o = new UserProduct();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setProductId(rs.getLong("productid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
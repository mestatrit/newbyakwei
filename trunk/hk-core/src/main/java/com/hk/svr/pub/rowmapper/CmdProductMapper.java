package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmdProduct;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmdProductMapper extends HkRowMapper<CmdProduct> {
	@Override
	public Class<CmdProduct> getMapperClass() {
		return CmdProduct.class;
	}

	public CmdProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmdProduct o = new CmdProduct();
		o.setOid(rs.getLong("oid"));
		o.setPcityId(rs.getInt("pcityid"));
		o.setProductId(rs.getLong("productid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
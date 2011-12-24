package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductAttr;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductAttrMapper extends HkRowMapper<CmpProductAttr> {

	@Override
	public Class<CmpProductAttr> getMapperClass() {
		return CmpProductAttr.class;
	}

	public CmpProductAttr mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpProductAttr o = new CmpProductAttr();
		o.setProductId(rs.getLong("productid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setAttr1(rs.getLong("attr1"));
		o.setAttr2(rs.getLong("attr2"));
		o.setAttr3(rs.getLong("attr3"));
		o.setAttr4(rs.getLong("attr4"));
		o.setAttr5(rs.getLong("attr5"));
		o.setAttr6(rs.getLong("attr6"));
		o.setAttr7(rs.getLong("attr7"));
		o.setAttr8(rs.getLong("attr8"));
		o.setAttr9(rs.getLong("attr9"));
		return o;
	}
}
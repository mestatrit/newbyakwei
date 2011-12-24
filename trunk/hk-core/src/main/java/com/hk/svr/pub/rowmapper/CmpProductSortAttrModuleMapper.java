package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductSortAttrModule;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductSortAttrModuleMapper extends
		HkRowMapper<CmpProductSortAttrModule> {

	@Override
	public Class<CmpProductSortAttrModule> getMapperClass() {
		return CmpProductSortAttrModule.class;
	}

	public CmpProductSortAttrModule mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpProductSortAttrModule o = new CmpProductSortAttrModule();
		o.setSortId(rs.getInt("sortid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setAttrName(rs.getString("attrname"));
		return o;
	}
}
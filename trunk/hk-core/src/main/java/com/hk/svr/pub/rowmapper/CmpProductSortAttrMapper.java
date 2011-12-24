package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductSortAttr;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductSortAttrMapper extends HkRowMapper<CmpProductSortAttr> {

	@Override
	public Class<CmpProductSortAttr> getMapperClass() {
		return CmpProductSortAttr.class;
	}

	public CmpProductSortAttr mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpProductSortAttr o = new CmpProductSortAttr();
		o.setAttrId(rs.getLong("attrid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setSortId(rs.getLong("sortid"));
		o.setAttrflg(rs.getInt("attrflg"));
		o.setName(rs.getString("name"));
		return o;
	}
}
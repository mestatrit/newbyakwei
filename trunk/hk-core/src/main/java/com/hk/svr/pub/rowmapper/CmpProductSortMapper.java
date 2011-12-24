package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductSort;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductSortMapper extends HkRowMapper<CmpProductSort> {

	@Override
	public Class<CmpProductSort> getMapperClass() {
		return CmpProductSort.class;
	}

	public CmpProductSort mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpProductSort o = new CmpProductSort();
		o.setSortId(rs.getInt("sortid"));
		o.setName(rs.getString("name"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setProductCount(rs.getInt("productcount"));
		o.setParentId(rs.getInt("parentid"));
		o.setChildflg(rs.getByte("childflg"));
		o.setNlevel(rs.getInt("nlevel"));
		o.setParentData(rs.getString("parentdata"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpTableSort;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpTableSortMapper extends HkRowMapper<CmpTableSort> {
	@Override
	public Class<CmpTableSort> getMapperClass() {
		return CmpTableSort.class;
	}

	public CmpTableSort mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpTableSort o = new CmpTableSort();
		o.setSortId(rs.getLong("sortid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
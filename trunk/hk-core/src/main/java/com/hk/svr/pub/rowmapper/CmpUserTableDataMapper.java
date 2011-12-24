package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUserTableData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUserTableDataMapper extends HkRowMapper<CmpUserTableData> {

	@Override
	public Class<CmpUserTableData> getMapperClass() {
		return CmpUserTableData.class;
	}

	public CmpUserTableData mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpUserTableData o = new CmpUserTableData();
		o.setDataId(rs.getLong("dataid"));
		o.setCmpNavOid(rs.getLong("cmpnavoid"));
		o.setTitle(rs.getString("title"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
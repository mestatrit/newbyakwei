package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpTableDailyData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpTableDailyDataMapper extends HkRowMapper<CmpTableDailyData> {
	@Override
	public Class<CmpTableDailyData> getMapperClass() {
		return CmpTableDailyData.class;
	}

	public CmpTableDailyData mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpTableDailyData o = new CmpTableDailyData();
		o.setDataId(rs.getLong("dataid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setSortId(rs.getLong("sortid"));
		o.setTableId(rs.getLong("tableid"));
		o.setPbcount(rs.getInt("pbcount"));
		o.setCreateDate(rs.getTimestamp("createdate"));
		return o;
	}
}
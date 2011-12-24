package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUserTableDataValue;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUserTableDataValueMapper extends
		HkRowMapper<CmpUserTableDataValue> {

	@Override
	public Class<CmpUserTableDataValue> getMapperClass() {
		return CmpUserTableDataValue.class;
	}

	public CmpUserTableDataValue mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpUserTableDataValue o = new CmpUserTableDataValue();
		o.setVid(rs.getLong("vid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setDataId(rs.getLong("dataid"));
		o.setData(rs.getString("data"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
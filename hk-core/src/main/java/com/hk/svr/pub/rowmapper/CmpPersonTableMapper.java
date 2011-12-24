package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpPersonTable;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpPersonTableMapper extends HkRowMapper<CmpPersonTable> {
	@Override
	public Class<CmpPersonTable> getMapperClass() {
		return CmpPersonTable.class;
	}

	public CmpPersonTable mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpPersonTable o = new CmpPersonTable();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setPersonNum(rs.getInt("personnum"));
		o.setFreeCount(rs.getInt("freecount"));
		o.setTotalCount(rs.getInt("totalcount"));
		o.setSortId(rs.getLong("sortid"));
		return o;
	}
}
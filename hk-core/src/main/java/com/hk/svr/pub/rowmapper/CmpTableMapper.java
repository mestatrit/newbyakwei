package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpTable;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpTableMapper extends HkRowMapper<CmpTable> {
	@Override
	public Class<CmpTable> getMapperClass() {
		return CmpTable.class;
	}

	public CmpTable mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpTable o = new CmpTable();
		o.setTableId(rs.getLong("tableid"));
		o.setSortId(rs.getLong("sortid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setTableNum(rs.getString("tablenum"));
		o.setIntro(rs.getString("intro"));
		o.setBestPersonNum(rs.getInt("bestpersonnum"));
		o.setMostPersonNum(rs.getInt("mostpersonnum"));
		o.setLeastPrice(rs.getDouble("leastprice"));
		o.setOpname(rs.getString("opname"));
		o.setNetOrderflg(rs.getByte("netorderflg"));
		o.setFreeflg(rs.getByte("freeflg"));
		o.setSetId(rs.getLong("setid"));
		o.setOrderflg(rs.getInt("orderflg"));
		return o;
	}
}
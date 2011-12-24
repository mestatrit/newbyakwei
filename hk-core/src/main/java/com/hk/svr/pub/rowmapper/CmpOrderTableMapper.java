package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrderTable;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrderTableMapper extends HkRowMapper<CmpOrderTable> {
	@Override
	public Class<CmpOrderTable> getMapperClass() {
		return CmpOrderTable.class;
	}

	public CmpOrderTable mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpOrderTable o = new CmpOrderTable();
		o.setOid(rs.getLong("oid"));
		o.setTableId(rs.getLong("tableid"));
		o.setBeginTime(rs.getTimestamp("begintime"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setName(rs.getString("name"));
		o.setTel(rs.getString("tel"));
		o.setRemark(rs.getString("remark"));
		o.setPersonNum(rs.getInt("personnum"));
		o.setObjstatus(rs.getByte("objstatus"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setMealTime(rs.getTimestamp("mealtime"));
		return o;
	}
}
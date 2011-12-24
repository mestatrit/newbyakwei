package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_UserItemMonthReport;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_UserItemMonthReportMapper extends
		HkRowMapper<Tb_UserItemMonthReport> {

	@Override
	public Class<Tb_UserItemMonthReport> getMapperClass() {
		return Tb_UserItemMonthReport.class;
	}

	public Tb_UserItemMonthReport mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		Tb_UserItemMonthReport o = new Tb_UserItemMonthReport();
		o.setOid(rs.getLong("oid"));
		o.setUserid(rs.getLong("userid"));
		o.setItem_num(rs.getInt("item_num"));
		o.setCreate_month(rs.getTimestamp("create_month"));
		return o;
	}
}
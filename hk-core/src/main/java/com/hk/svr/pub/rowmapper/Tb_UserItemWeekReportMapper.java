package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_UserItemWeekReport;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_UserItemWeekReportMapper extends
		HkRowMapper<Tb_UserItemWeekReport> {

	@Override
	public Class<Tb_UserItemWeekReport> getMapperClass() {
		return Tb_UserItemWeekReport.class;
	}

	public Tb_UserItemWeekReport mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		Tb_UserItemWeekReport o = new Tb_UserItemWeekReport();
		o.setOid(rs.getLong("oid"));
		o.setUserid(rs.getLong("userid"));
		o.setItem_num(rs.getInt("item_num"));
		o.setCreate_week(rs.getTimestamp("create_week"));
		return o;
	}
}
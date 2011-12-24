package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_UserItemDailyReport;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_UserItemDailyReportMapper extends
		HkRowMapper<Tb_UserItemDailyReport> {

	@Override
	public Class<Tb_UserItemDailyReport> getMapperClass() {
		return Tb_UserItemDailyReport.class;
	}

	public Tb_UserItemDailyReport mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		Tb_UserItemDailyReport o = new Tb_UserItemDailyReport();
		o.setOid(rs.getLong("oid"));
		o.setUserid(rs.getLong("userid"));
		o.setItem_num(rs.getInt("item_num"));
		o.setCreate_date(rs.getTimestamp("create_date"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.InviteCodeData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class InviteCodeDataMapper extends HkRowMapper<InviteCodeData> {

	@Override
	public Class<InviteCodeData> getMapperClass() {
		return InviteCodeData.class;
	}

	public InviteCodeData mapRow(ResultSet rs, int rowNum) throws SQLException {
		InviteCodeData o = new InviteCodeData();
		o.setOid(rs.getInt("oid"));
		o.setData(rs.getString("data"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.ApiInvokeRate;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ApiInvokeRateMapper extends HkRowMapper<ApiInvokeRate> {

	@Override
	public Class<ApiInvokeRate> getMapperClass() {
		return ApiInvokeRate.class;
	}

	@Override
	public ApiInvokeRate mapRow(ResultSet rs, int rowNum) throws SQLException {
		ApiInvokeRate o = new ApiInvokeRate();
		o.setOid(rs.getInt("oid"));
		o.setRate(rs.getInt("rate"));
		return o;
	}
}
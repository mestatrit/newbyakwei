package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.ApiInvoke;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ApiInvokeMapper extends HkRowMapper<ApiInvoke> {

	@Override
	public Class<ApiInvoke> getMapperClass() {
		return ApiInvoke.class;
	}

	@Override
	public ApiInvoke mapRow(ResultSet rs, int rowNum) throws SQLException {
		ApiInvoke o = new ApiInvoke();
		o.setOid(rs.getInt("oid"));
		o.setInvoke_count(rs.getInt("invoke_count"));
		o.setTime(rs.getTimestamp("time"));
		o.setTestflg(rs.getByte("testflg"));
		return o;
	}
}
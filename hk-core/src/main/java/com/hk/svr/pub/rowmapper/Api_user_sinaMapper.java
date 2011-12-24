package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.Api_user_sina;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Api_user_sinaMapper extends HkRowMapper<Api_user_sina> {

	@Override
	public Class<Api_user_sina> getMapperClass() {
		return Api_user_sina.class;
	}

	public Api_user_sina mapRow(ResultSet rs, int rowNum) throws SQLException {
		Api_user_sina o = new Api_user_sina();
		o.setSina_userid(rs.getLong("sina_userid"));
		o.setAccess_token(rs.getString("access_token"));
		o.setToken_secret(rs.getString("token_secret"));
		o.setUserid(rs.getLong("userid"));
		return o;
	}
}
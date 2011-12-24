package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_User_Api;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_User_ApiMapper extends HkRowMapper<Tb_User_Api> {

	@Override
	public Class<Tb_User_Api> getMapperClass() {
		return Tb_User_Api.class;
	}

	public Tb_User_Api mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_User_Api o = new Tb_User_Api();
		o.setOid(rs.getLong("oid"));
		o.setUserid(rs.getLong("userid"));
		o.setLogin_name(rs.getString("login_name"));
		o.setLogin_pwd(rs.getString("login_pwd"));
		o.setAccess_token(rs.getString("access_token"));
		o.setToken_secret(rs.getString("token_secret"));
		o.setUid(rs.getString("uid"));
		o.setReg_source(rs.getByte("reg_source"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Follow;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_FollowMapper extends HkRowMapper<Tb_Follow> {

	@Override
	public Class<Tb_Follow> getMapperClass() {
		return Tb_Follow.class;
	}

	public Tb_Follow mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Follow o = new Tb_Follow();
		o.setOid(rs.getLong("oid"));
		o.setUserid(rs.getLong("userid"));
		o.setFriendid(rs.getLong("friendid"));
		return o;
	}
}
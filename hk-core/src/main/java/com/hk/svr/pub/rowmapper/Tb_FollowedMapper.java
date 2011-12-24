package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Followed;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_FollowedMapper extends HkRowMapper<Tb_Followed> {

	@Override
	public Class<Tb_Followed> getMapperClass() {
		return Tb_Followed.class;
	}

	public Tb_Followed mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Followed o = new Tb_Followed();
		o.setOid(rs.getLong("oid"));
		o.setUserid(rs.getLong("userid"));
		o.setFansid(rs.getLong("fansid"));
		return o;
	}
}
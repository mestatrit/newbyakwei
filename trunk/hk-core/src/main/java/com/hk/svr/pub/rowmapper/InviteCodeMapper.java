package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.InviteCode;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class InviteCodeMapper extends HkRowMapper<InviteCode> {

	@Override
	public Class<InviteCode> getMapperClass() {
		return InviteCode.class;
	}

	public InviteCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		InviteCode o = new InviteCode();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setData(rs.getString("data"));
		o.setUseflg(rs.getByte("useflg"));
		return o;
	}
}
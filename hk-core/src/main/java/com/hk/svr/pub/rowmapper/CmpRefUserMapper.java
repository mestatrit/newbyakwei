package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpRefUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpRefUserMapper extends HkRowMapper<CmpRefUser> {

	@Override
	public Class<CmpRefUser> getMapperClass() {
		return CmpRefUser.class;
	}

	public CmpRefUser mapRow(ResultSet rs, int arg1) throws SQLException {
		CmpRefUser o = new CmpRefUser();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setJoinflg(rs.getByte("joinflg"));
		return o;
	}
}
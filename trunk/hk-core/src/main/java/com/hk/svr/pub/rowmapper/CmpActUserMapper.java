package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActUserMapper extends HkRowMapper<CmpActUser> {
	@Override
	public Class<CmpActUser> getMapperClass() {
		return CmpActUser.class;
	}

	public CmpActUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActUser o = new CmpActUser();
		o.setOid(rs.getLong("oid"));
		o.setActId(rs.getLong("actid"));
		o.setUserId(rs.getLong("userid"));
		o.setCheckflg(rs.getByte("checkflg"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
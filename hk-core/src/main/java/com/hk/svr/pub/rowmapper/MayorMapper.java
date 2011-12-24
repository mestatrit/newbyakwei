package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.Mayor;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class MayorMapper extends HkRowMapper<Mayor> {

	@Override
	public Class<Mayor> getMapperClass() {
		return Mayor.class;
	}

	public Mayor mapRow(ResultSet rs, int rowNum) throws SQLException {
		Mayor o = new Mayor();
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setMayorId(rs.getLong("mayorid"));
		o.setPcityId(rs.getInt("pcityid"));
		return o;
	}
}
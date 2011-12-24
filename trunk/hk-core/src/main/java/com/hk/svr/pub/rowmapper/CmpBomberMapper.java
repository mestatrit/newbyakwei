package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpBomber;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBomberMapper extends HkRowMapper<CmpBomber> {

	@Override
	public Class<CmpBomber> getMapperClass() {
		return CmpBomber.class;
	}

	public CmpBomber mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpBomber o = new CmpBomber();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setBombcount(rs.getInt("bombcount"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmdCmp;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmdCmpMapper extends HkRowMapper<CmdCmp> {
	@Override
	public Class<CmdCmp> getMapperClass() {
		return CmdCmp.class;
	}

	public CmdCmp mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmdCmp o = new CmdCmp();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setPcityId(rs.getInt("pcityid"));
		return o;
	}
}
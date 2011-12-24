package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpAdBlock;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpAdBlockMapper extends HkRowMapper<CmpAdBlock> {

	@Override
	public Class<CmpAdBlock> getMapperClass() {
		return CmpAdBlock.class;
	}

	public CmpAdBlock mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpAdBlock o = new CmpAdBlock();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setBlockId(rs.getLong("blockid"));
		o.setAdid(rs.getLong("adid"));
		o.setPageflg(rs.getByte("pageflg"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpLink;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpLinkMapper extends HkRowMapper<CmpLink> {
	@Override
	public Class<CmpLink> getMapperClass() {
		return CmpLink.class;
	}

	public CmpLink mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpLink o = new CmpLink();
		o.setLinkId(rs.getInt("linkid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setLinkCompanyId(rs.getLong("linkcompanyid"));
		return o;
	}
}
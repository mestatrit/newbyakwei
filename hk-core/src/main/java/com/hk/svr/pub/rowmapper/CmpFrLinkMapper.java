package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpFrLink;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpFrLinkMapper extends HkRowMapper<CmpFrLink> {

	@Override
	public Class<CmpFrLink> getMapperClass() {
		return CmpFrLink.class;
	}

	public CmpFrLink mapRow(ResultSet rs, int arg1) throws SQLException {
		CmpFrLink o = new CmpFrLink();
		o.setLinkId(rs.getLong("linkid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setUrl(rs.getString("url"));
		return o;
	}
}
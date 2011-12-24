package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpAd;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpAdMapper extends HkRowMapper<CmpAd> {

	@Override
	public Class<CmpAd> getMapperClass() {
		return CmpAd.class;
	}

	public CmpAd mapRow(ResultSet rs, int arg1) throws SQLException {
		CmpAd o = new CmpAd();
		o.setCompanyId(rs.getLong("companyid"));
		o.setAdid(rs.getLong("adid"));
		o.setPath(rs.getString("path"));
		o.setUrl(rs.getString("url"));
		o.setName(rs.getString("name"));
		o.setAdflg(rs.getByte("adflg"));
		o.setPage1BlockId(rs.getLong("page1blockid"));
		o.setGroupId(rs.getLong("groupid"));
		o.setHtml(rs.getString("html"));
		return o;
	}
}
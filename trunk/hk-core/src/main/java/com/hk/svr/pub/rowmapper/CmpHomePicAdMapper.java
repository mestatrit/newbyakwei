package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpHomePicAd;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpHomePicAdMapper extends HkRowMapper<CmpHomePicAd> {

	@Override
	public Class<CmpHomePicAd> getMapperClass() {
		return CmpHomePicAd.class;
	}

	public CmpHomePicAd mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpHomePicAd o = new CmpHomePicAd();
		o.setAdid(rs.getLong("adid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setPath(rs.getString("path"));
		o.setTitle(rs.getString("title"));
		o.setUrl(rs.getString("url"));
		return o;
	}
}
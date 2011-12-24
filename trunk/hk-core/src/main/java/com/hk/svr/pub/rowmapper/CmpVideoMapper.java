package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpVideo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpVideoMapper extends HkRowMapper<CmpVideo> {

	@Override
	public Class<CmpVideo> getMapperClass() {
		return CmpVideo.class;
	}

	public CmpVideo mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpVideo o = new CmpVideo();
		o.setOid(rs.getLong("oid"));
		o.setName(rs.getString("name"));
		o.setHtml(rs.getString("html"));
		o.setIntro(rs.getString("intro"));
		o.setPath(rs.getString("path"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setCmpNavOid(rs.getLong("cmpnavoid"));
		o.setFileSize(rs.getLong("filesize"));
		return o;
	}
}
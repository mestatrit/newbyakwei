package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrgArticleContent;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgArticleContentMapper extends
		HkRowMapper<CmpOrgArticleContent> {

	@Override
	public Class<CmpOrgArticleContent> getMapperClass() {
		return CmpOrgArticleContent.class;
	}

	public CmpOrgArticleContent mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpOrgArticleContent o = new CmpOrgArticleContent();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setOrgId(rs.getLong("orgid"));
		o.setContent(rs.getString("content"));
		return o;
	}
}
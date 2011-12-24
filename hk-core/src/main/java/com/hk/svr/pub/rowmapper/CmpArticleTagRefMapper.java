package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpArticleTagRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpArticleTagRefMapper extends HkRowMapper<CmpArticleTagRef> {

	@Override
	public Class<CmpArticleTagRef> getMapperClass() {
		return CmpArticleTagRef.class;
	}

	public CmpArticleTagRef mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpArticleTagRef o = new CmpArticleTagRef();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setTagId(rs.getLong("tagid"));
		o.setArticleId(rs.getLong("articleid"));
		return o;
	}
}
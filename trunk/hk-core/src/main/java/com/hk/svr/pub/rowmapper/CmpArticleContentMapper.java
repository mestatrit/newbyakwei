package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpArticleContent;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpArticleContentMapper extends HkRowMapper<CmpArticleContent> {

	@Override
	public Class<CmpArticleContent> getMapperClass() {
		return CmpArticleContent.class;
	}

	public CmpArticleContent mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpArticleContent o = new CmpArticleContent();
		o.setOid(rs.getLong("oid"));
		o.setContent(rs.getString("content"));
		return o;
	}
}
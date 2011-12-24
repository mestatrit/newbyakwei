package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpArticleTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpArticleTagMapper extends HkRowMapper<CmpArticleTag> {

	@Override
	public Class<CmpArticleTag> getMapperClass() {
		return CmpArticleTag.class;
	}

	public CmpArticleTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpArticleTag o = new CmpArticleTag();
		o.setTagId(rs.getLong("tagid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setPinkflg(rs.getByte("pinkflg"));
		o.setPinktime(rs.getTimestamp("pinktime"));
		return o;
	}
}
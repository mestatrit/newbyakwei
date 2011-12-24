package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpArticleNavPink;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpArticleNavPinkMapper extends HkRowMapper<CmpArticleNavPink> {

	@Override
	public Class<CmpArticleNavPink> getMapperClass() {
		return CmpArticleNavPink.class;
	}

	public CmpArticleNavPink mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpArticleNavPink o = new CmpArticleNavPink();
		o.setOid(rs.getLong("oid"));
		o.setArticleId(rs.getLong("articleid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setNavId(rs.getLong("navid"));
		o.setPflg(rs.getInt("pflg"));
		o.setOrderflg(rs.getInt("orderflg"));
		return o;
	}
}
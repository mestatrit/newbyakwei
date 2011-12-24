package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrgArticle;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgArticleMapper extends HkRowMapper<CmpOrgArticle> {

	@Override
	public Class<CmpOrgArticle> getMapperClass() {
		return CmpOrgArticle.class;
	}

	public CmpOrgArticle mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpOrgArticle o = new CmpOrgArticle();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setNavId(rs.getLong("navid"));
		o.setTitle(rs.getString("title"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setPath(rs.getString("path"));
		o.setOrderflg(rs.getInt("orderflg"));
		o.setHideTitleflg(rs.getByte("hidetitleflg"));
		o.setOrgId(rs.getLong("orgid"));
		return o;
	}
}
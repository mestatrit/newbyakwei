package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpArticle;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpArticleMapper extends HkRowMapper<CmpArticle> {

	@Override
	public Class<CmpArticle> getMapperClass() {
		return CmpArticle.class;
	}

	public CmpArticle mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpArticle o = new CmpArticle();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setTitle(rs.getString("title"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setFilepath(rs.getString("filepath"));
		o.setCmpNavOid(rs.getLong("cmpnavoid"));
		o.setCmppink(rs.getByte("cmppink"));
		o.setCmppinkTime(rs.getTimestamp("cmppinktime"));
		o.setHomepink(rs.getByte("homepink"));
		o.setOrderflg(rs.getInt("orderflg"));
		o.setProductId(rs.getLong("productid"));
		o.setSortId(rs.getInt("sortid"));
		o.setHideTitleflg(rs.getByte("hidetitleflg"));
		o.setGroupId(rs.getLong("groupid"));
		o.setPage1BlockId(rs.getLong("page1blockid"));
		return o;
	}
}
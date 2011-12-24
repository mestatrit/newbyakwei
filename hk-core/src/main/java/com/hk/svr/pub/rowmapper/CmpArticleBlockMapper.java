package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpArticleBlock;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpArticleBlockMapper extends HkRowMapper<CmpArticleBlock> {

	@Override
	public Class<CmpArticleBlock> getMapperClass() {
		return CmpArticleBlock.class;
	}

	public CmpArticleBlock mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpArticleBlock o = new CmpArticleBlock();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setBlockId(rs.getLong("blockid"));
		o.setArticleId(rs.getLong("articleid"));
		o.setPageflg(rs.getByte("pageflg"));
		o.setCmpNavOid(rs.getLong("cmpnavoid"));
		return o;
	}
}
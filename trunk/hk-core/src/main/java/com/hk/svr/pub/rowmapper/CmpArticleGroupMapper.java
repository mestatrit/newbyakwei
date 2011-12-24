package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpArticleGroup;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpArticleGroupMapper extends HkRowMapper<CmpArticleGroup> {

	@Override
	public Class<CmpArticleGroup> getMapperClass() {
		return CmpArticleGroup.class;
	}

	public CmpArticleGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpArticleGroup o = new CmpArticleGroup();
		o.setGroupId(rs.getLong("groupid"));
		o.setName(rs.getString("name"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setCmpNavOid(rs.getLong("cmpnavoid"));
		return o;
	}
}
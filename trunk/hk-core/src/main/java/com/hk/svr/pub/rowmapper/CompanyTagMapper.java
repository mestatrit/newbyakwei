package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyTagMapper extends HkRowMapper<CompanyTag> {
	@Override
	public Class<CompanyTag> getMapperClass() {
		return CompanyTag.class;
	}

	public CompanyTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyTag o = new CompanyTag();
		o.setTagId(rs.getInt("tagid"));
		o.setName(rs.getString("name"));
		o.setKindId(rs.getInt("kindid"));
		return o;
	}
}
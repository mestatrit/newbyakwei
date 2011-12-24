package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpBbsContent;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBbsContentMapper extends HkRowMapper<CmpBbsContent> {

	@Override
	public Class<CmpBbsContent> getMapperClass() {
		return CmpBbsContent.class;
	}

	public CmpBbsContent mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpBbsContent o = new CmpBbsContent();
		o.setBbsId(rs.getLong("bbsid"));
		o.setContent(rs.getString("content"));
		return o;
	}
}
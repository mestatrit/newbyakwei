package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpRecruit;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpRecruitMapper extends HkRowMapper<CmpRecruit> {
	@Override
	public Class<CmpRecruit> getMapperClass() {
		return CmpRecruit.class;
	}

	public CmpRecruit mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpRecruit o = new CmpRecruit();
		o.setCompanyId(rs.getLong("companyid"));
		o.setTitle(rs.getString("title"));
		o.setContent(rs.getString("content"));
		return o;
	}
}
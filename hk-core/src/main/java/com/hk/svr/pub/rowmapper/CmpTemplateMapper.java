package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpTemplate;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpTemplateMapper extends HkRowMapper<CmpTemplate> {
	@Override
	public Class<CmpTemplate> getMapperClass() {
		return CmpTemplate.class;
	}

	public CmpTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpTemplate o = new CmpTemplate();
		o.setCompanyId(rs.getLong("companyid"));
		o.setTemplateId(rs.getInt("templateid"));
		return o;
	}
}
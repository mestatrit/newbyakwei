package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrgStudyAdContent;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgStudyAdContentMapper extends
		HkRowMapper<CmpOrgStudyAdContent> {

	@Override
	public Class<CmpOrgStudyAdContent> getMapperClass() {
		return CmpOrgStudyAdContent.class;
	}

	public CmpOrgStudyAdContent mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpOrgStudyAdContent o = new CmpOrgStudyAdContent();
		o.setAdid(rs.getLong("adid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setOrgId(rs.getLong("orgid"));
		o.setContent(rs.getString("content"));
		return o;
	}
}
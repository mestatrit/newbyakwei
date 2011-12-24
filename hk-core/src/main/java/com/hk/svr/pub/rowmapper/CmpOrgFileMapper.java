package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrgFile;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgFileMapper extends HkRowMapper<CmpOrgFile> {

	@Override
	public Class<CmpOrgFile> getMapperClass() {
		return CmpOrgFile.class;
	}

	public CmpOrgFile mapRow(ResultSet rs, int arg1) throws SQLException {
		CmpOrgFile o = new CmpOrgFile();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setArticleOid(rs.getLong("articleoid"));
		o.setOrgId(rs.getLong("orgid"));
		o.setPath(rs.getString("path"));
		o.setTopflg(rs.getByte("topflg"));
		return o;
	}
}
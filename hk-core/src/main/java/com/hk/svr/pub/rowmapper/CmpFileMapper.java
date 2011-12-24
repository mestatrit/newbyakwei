package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpFile;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpFileMapper extends HkRowMapper<CmpFile> {

	@Override
	public Class<CmpFile> getMapperClass() {
		return CmpFile.class;
	}

	public CmpFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpFile o = new CmpFile();
		o.setOid(rs.getLong("oid"));
		o.setArticleOid(rs.getLong("articleoid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setFileflg(rs.getByte("fileflg"));
		o.setPath(rs.getString("path"));
		o.setTopflg(rs.getByte("topflg"));
		return o;
	}
}
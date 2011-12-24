package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductSortFile;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductSortFileMapper extends HkRowMapper<CmpProductSortFile> {

	@Override
	public Class<CmpProductSortFile> getMapperClass() {
		return CmpProductSortFile.class;
	}

	public CmpProductSortFile mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpProductSortFile o = new CmpProductSortFile();
		o.setOid(rs.getLong("oid"));
		o.setSortId(rs.getInt("sortid"));
		o.setPath(rs.getString("path"));
		o.setUrl(rs.getString("url"));
		o.setName(rs.getString("name"));
		o.setCompanyId(rs.getLong("companyId"));
		return o;
	}
}
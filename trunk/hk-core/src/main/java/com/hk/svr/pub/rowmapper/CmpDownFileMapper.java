package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpDownFile;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpDownFileMapper extends HkRowMapper<CmpDownFile> {

	@Override
	public Class<CmpDownFile> getMapperClass() {
		return CmpDownFile.class;
	}

	public CmpDownFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpDownFile o = new CmpDownFile();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setCmpNavOid(rs.getLong("cmpnavoid"));
		o.setName(rs.getString("name"));
		o.setPath(rs.getString("path"));
		o.setDcount(rs.getInt("dcount"));
		o.setFileSize(rs.getLong("filesize"));
		return o;
	}
}
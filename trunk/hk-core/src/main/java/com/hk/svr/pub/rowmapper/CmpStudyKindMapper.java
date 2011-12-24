package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpStudyKind;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpStudyKindMapper extends HkRowMapper<CmpStudyKind> {

	@Override
	public Class<CmpStudyKind> getMapperClass() {
		return CmpStudyKind.class;
	}

	public CmpStudyKind mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpStudyKind o = new CmpStudyKind();
		o.setKindId(rs.getLong("kindid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setParentId(rs.getLong("parentid"));
		o.setChildflg(rs.getByte("childflg"));
		o.setKlevel(rs.getInt("klevel"));
		return o;
	}
}
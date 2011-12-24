package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpFuncRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpFuncRefMapper extends HkRowMapper<CmpFuncRef> {

	@Override
	public Class<CmpFuncRef> getMapperClass() {
		return CmpFuncRef.class;
	}

	public CmpFuncRef mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpFuncRef o = new CmpFuncRef();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setFuncoid(rs.getLong("funcoid"));
		o.setCnfdata(rs.getString("cnfdata"));
		return o;
	}
}
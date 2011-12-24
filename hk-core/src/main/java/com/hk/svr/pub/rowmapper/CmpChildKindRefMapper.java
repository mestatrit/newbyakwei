package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpChildKindRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpChildKindRefMapper extends HkRowMapper<CmpChildKindRef> {
	@Override
	public Class<CmpChildKindRef> getMapperClass() {
		return CmpChildKindRef.class;
	}

	public CmpChildKindRef mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpChildKindRef o = new CmpChildKindRef();
		o.setCompanyId(rs.getLong("companyid"));
		o.setOid(rs.getInt("oid"));
		o.setCityId(rs.getInt("cityid"));
		return o;
	}
}
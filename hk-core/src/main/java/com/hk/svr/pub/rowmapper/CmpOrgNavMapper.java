package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrgNav;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgNavMapper extends HkRowMapper<CmpOrgNav> {

	@Override
	public Class<CmpOrgNav> getMapperClass() {
		return CmpOrgNav.class;
	}

	public CmpOrgNav mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpOrgNav o = new CmpOrgNav();
		o.setNavId(rs.getLong("navid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setOrgId(rs.getLong("orgid"));
		o.setName(rs.getString("name"));
		o.setReffunc(rs.getInt("reffunc"));
		o.setOrderflg(rs.getInt("orderflg"));
		o.setShowflg(rs.getByte("showflg"));
		return o;
	}
}
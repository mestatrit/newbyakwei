package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpMyBbs;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpMyBbsMapper extends HkRowMapper<CmpMyBbs> {

	@Override
	public Class<CmpMyBbs> getMapperClass() {
		return CmpMyBbs.class;
	}

	public CmpMyBbs mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpMyBbs o = new CmpMyBbs();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setBbsId(rs.getLong("bbsid"));
		o.setBbsflg(rs.getByte("bbsflg"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpSvrCnf;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpSvrCnfMapper extends HkRowMapper<CmpSvrCnf> {

	@Override
	public Class<CmpSvrCnf> getMapperClass() {
		return CmpSvrCnf.class;
	}

	public CmpSvrCnf mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpSvrCnf o = new CmpSvrCnf();
		o.setCompanyId(rs.getLong("companyid"));
		o.setFileflg(rs.getByte("fileflg"));
		o.setVideoflg(rs.getByte("videoflg"));
		return o;
	}
}
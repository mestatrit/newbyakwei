package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpSvr;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpSvrMapper extends HkRowMapper<CmpSvr> {

	@Override
	public Class<CmpSvr> getMapperClass() {
		return CmpSvr.class;
	}

	public CmpSvr mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpSvr o = new CmpSvr();
		o.setSvrId(rs.getLong("svrid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setPhotosetId(rs.getLong("photosetid"));
		o.setPrice(rs.getDouble("price"));
		o.setSvrmin(rs.getInt("svrmin"));
		o.setIntro(rs.getString("intro"));
		o.setKindId(rs.getLong("kindid"));
		return o;
	}
}
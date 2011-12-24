package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpInfoMapper extends HkRowMapper<CmpInfo> {

	@Override
	public Class<CmpInfo> getMapperClass() {
		return CmpInfo.class;
	}

	public CmpInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpInfo o = new CmpInfo();
		o.setCompanyId(rs.getLong("companyid"));
		o.setDomain(rs.getString("domain"));
		o.setStyleData(rs.getString("styledata"));
		o.setStyleflg(rs.getByte("styleflg"));
		o.setLanguage(rs.getInt("language"));
		o.setArticlead(rs.getString("articlead"));
		o.setColumnad(rs.getString("columnad"));
		o.setTmlflg(rs.getInt("tmlflg"));
		o.setBgPicPath(rs.getString("bgpicpath"));
		o.setCpinfo(rs.getString("cpinfo"));
		return o;
	}
}
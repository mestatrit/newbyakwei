package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrg;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgMapper extends HkRowMapper<CmpOrg> {

	@Override
	public Class<CmpOrg> getMapperClass() {
		return CmpOrg.class;
	}

	public CmpOrg mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpOrg o = new CmpOrg();
		o.setOrgId(rs.getLong("orgid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setUserId(rs.getLong("userid"));
		o.setFlg(rs.getByte("flg"));
		o.setUserinfoflg(rs.getByte("userinfoflg"));
		o.setPath(rs.getString("path"));
		o.setStyleData(rs.getString("styledata"));
		o.setStyleflg(rs.getByte("styleflg"));
		o.setCityId(rs.getInt("cityid"));
		return o;
	}
}
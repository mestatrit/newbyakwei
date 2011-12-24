package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrgStudyAd;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgStudyAdMapper extends HkRowMapper<CmpOrgStudyAd> {

	@Override
	public Class<CmpOrgStudyAd> getMapperClass() {
		return CmpOrgStudyAd.class;
	}

	public CmpOrgStudyAd mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpOrgStudyAd o = new CmpOrgStudyAd();
		o.setAdid(rs.getLong("adid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setOrgId(rs.getLong("orgid"));
		o.setTitle(rs.getString("title"));
		o.setSchoolName(rs.getString("schoolname"));
		o.setBeginTime(rs.getTimestamp("begintime"));
		o.setAvailableTime(rs.getTimestamp("availabletime"));
		o.setStudyAddr(rs.getString("studyaddr"));
		o.setTeachType(rs.getString("teachtype"));
		o.setStudyUser(rs.getString("studyuser"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setPrice(rs.getString("price"));
		o.setKindId(rs.getLong("kindid"));
		return o;
	}
}
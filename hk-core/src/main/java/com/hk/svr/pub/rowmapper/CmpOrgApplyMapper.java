package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrgApply;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgApplyMapper extends HkRowMapper<CmpOrgApply> {

	@Override
	public Class<CmpOrgApply> getMapperClass() {
		return CmpOrgApply.class;
	}

	public CmpOrgApply mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpOrgApply o = new CmpOrgApply();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setOrgName(rs.getString("orgname"));
		o.setUserName(rs.getString("username"));
		o.setUserId(rs.getLong("userid"));
		o.setTel(rs.getString("tel"));
		o.setCheckflg(rs.getByte("checkflg"));
		o.setEmail(rs.getString("email"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setOrgId(rs.getLong("orgid"));
		o.setCityId(rs.getInt("cityid"));
		return o;
	}
}
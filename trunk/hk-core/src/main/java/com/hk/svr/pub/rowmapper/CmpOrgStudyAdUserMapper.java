package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrgStudyAdUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgStudyAdUserMapper extends HkRowMapper<CmpOrgStudyAdUser> {

	@Override
	public Class<CmpOrgStudyAdUser> getMapperClass() {
		return CmpOrgStudyAdUser.class;
	}

	public CmpOrgStudyAdUser mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpOrgStudyAdUser o = new CmpOrgStudyAdUser();
		o.setOid(rs.getLong("oid"));
		o.setAdid(rs.getLong("adid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setOrgId(rs.getLong("orgid"));
		o.setName(rs.getString("name"));
		o.setTel(rs.getString("tel"));
		o.setMobile(rs.getString("mobile"));
		o.setIm(rs.getString("im"));
		o.setCity(rs.getString("city"));
		o.setMsg(rs.getString("msg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setSex(rs.getByte("sex"));
		o.setEmail(rs.getString("email"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpMember;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpMemberMapper extends HkRowMapper<CmpMember> {
	@Override
	public Class<CmpMember> getMapperClass() {
		return CmpMember.class;
	}

	public CmpMember mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpMember o = new CmpMember();
		o.setMemberId(rs.getLong("memberid"));
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setMoney(rs.getDouble("money"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setMobile(rs.getString("mobile"));
		o.setEmail(rs.getString("email"));
		o.setGradeId(rs.getLong("gradeid"));
		o.setName(rs.getString("name"));
		return o;
	}
}
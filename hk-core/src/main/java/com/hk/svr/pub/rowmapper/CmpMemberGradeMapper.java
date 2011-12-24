package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpMemberGrade;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpMemberGradeMapper extends HkRowMapper<CmpMemberGrade> {
	@Override
	public Class<CmpMemberGrade> getMapperClass() {
		return CmpMemberGrade.class;
	}

	public CmpMemberGrade mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpMemberGrade o = new CmpMemberGrade();
		o.setGradeId(rs.getLong("gradeid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setRebate(rs.getDouble("rebate"));
		return o;
	}
}
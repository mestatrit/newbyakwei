package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyAward;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyAwardMapper extends HkRowMapper<CompanyAward> {
	@Override
	public Class<CompanyAward> getMapperClass() {
		return CompanyAward.class;
	}

	public CompanyAward mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyAward o = new CompanyAward();
		o.setCompanyId(rs.getLong("companyid"));
		o.setCreaterId(rs.getLong("createrid"));
		o.setAwardStatus(rs.getByte("awardstatus"));
		o.setMoney(rs.getInt("money"));
		o.setAwardhkb(rs.getInt("awardhkb"));
		return o;
	}
}
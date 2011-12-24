package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyMoney;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyMoneyMapper extends HkRowMapper<CompanyMoney> {
	@Override
	public Class<CompanyMoney> getMapperClass() {
		return CompanyMoney.class;
	}

	public CompanyMoney mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyMoney o = new CompanyMoney();
		o.setSysId(rs.getLong("sysid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setMoney(rs.getInt("money"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setFirstflg(rs.getByte("firstflg"));
		return o;
	}
}
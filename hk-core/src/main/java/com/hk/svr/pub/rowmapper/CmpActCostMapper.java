package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActCost;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActCostMapper extends HkRowMapper<CmpActCost> {
	@Override
	public Class<CmpActCost> getMapperClass() {
		return CmpActCost.class;
	}

	public CmpActCost mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActCost o = new CmpActCost();
		o.setCostId(rs.getLong("costid"));
		o.setActId(rs.getLong("actid"));
		o.setName(rs.getString("name"));
		o.setActCost(rs.getDouble("actcost"));
		o.setIntro(rs.getString("intro"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
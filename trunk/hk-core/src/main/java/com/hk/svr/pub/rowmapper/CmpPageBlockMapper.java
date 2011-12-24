package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpPageBlock;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpPageBlockMapper extends HkRowMapper<CmpPageBlock> {

	@Override
	public Class<CmpPageBlock> getMapperClass() {
		return CmpPageBlock.class;
	}

	public CmpPageBlock mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpPageBlock o = new CmpPageBlock();
		o.setBlockId(rs.getLong("blockid"));
		o.setName(rs.getString("name"));
		o.setPageModId(rs.getInt("pagemodid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setPageflg(rs.getByte("pageflg"));
		o.setOrderflg(rs.getInt("orderflg"));
		o.setExpression(rs.getString("expression"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpSellNet;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpSellNetMapper extends HkRowMapper<CmpSellNet> {

	@Override
	public Class<CmpSellNet> getMapperClass() {
		return CmpSellNet.class;
	}

	public CmpSellNet mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpSellNet o = new CmpSellNet();
		o.setOid(rs.getLong("oid"));
		o.setName(rs.getString("name"));
		o.setAddr(rs.getString("addr"));
		o.setTel(rs.getString("tel"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setOrderflg(rs.getInt("orderflg"));
		o.setKindId(rs.getLong("kindid"));
		o.setMapData(rs.getString("mapdata"));
		return o;
	}
}
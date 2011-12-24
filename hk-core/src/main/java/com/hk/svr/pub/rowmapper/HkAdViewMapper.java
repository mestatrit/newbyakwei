package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.HkAdView;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkAdViewMapper extends HkRowMapper<HkAdView> {

	@Override
	public Class<HkAdView> getMapperClass() {
		return HkAdView.class;
	}

	public HkAdView mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkAdView o = new HkAdView();
		o.setOid(rs.getLong("oid"));
		o.setAdoid(rs.getLong("adoid"));
		o.setViewerId(rs.getString("viewerid"));
		o.setIp(rs.getString("ip"));
		o.setUdate(rs.getInt("udate"));
		o.setUcount(rs.getInt("ucount"));
		return o;
	}
}
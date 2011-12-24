package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.ShortUrlData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ShortUrlDataMapper extends HkRowMapper<ShortUrlData> {
	@Override
	public Class<ShortUrlData> getMapperClass() {
		return ShortUrlData.class;
	}

	public ShortUrlData mapRow(ResultSet rs, int rowNum) throws SQLException {
		ShortUrlData o = new ShortUrlData();
		o.setOid(rs.getInt("oid"));
		o.setData(rs.getString("data"));
		return o;
	}
}
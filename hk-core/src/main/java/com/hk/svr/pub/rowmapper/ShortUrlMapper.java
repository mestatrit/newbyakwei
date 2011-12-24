package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.ShortUrl;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ShortUrlMapper extends HkRowMapper<ShortUrl> {
	@Override
	public Class<ShortUrl> getMapperClass() {
		return ShortUrl.class;
	}

	public ShortUrl mapRow(ResultSet rs, int rowNum) throws SQLException {
		ShortUrl o = new ShortUrl();
		o.setUrlId(rs.getLong("urlid"));
		o.setShortKey(rs.getString("shortkey"));
		o.setUrl(rs.getString("url"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setPcount(rs.getInt("pcount"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Feed;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class FeedMapper extends HkRowMapper<Feed> {
	@Override
	public Class<Feed> getMapperClass() {
		return Feed.class;
	}

	public Feed mapRow(ResultSet rs, int rowNum) throws SQLException {
		Feed o = new Feed();
		o.setFeedId(rs.getLong("feedid"));
		o.setUserId(rs.getLong("userid"));
		o.setFeedType(rs.getByte("feedtype"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setIpNumber(rs.getLong("ipnumber"));
		o.setRangeId(rs.getInt("rangeid"));
		o.setCityId(rs.getInt("cityid"));
		o.setData(rs.getString("data"));
		return o;
	}
}
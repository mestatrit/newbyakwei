package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.FeedInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class FeedInfoMapper extends HkRowMapper<FeedInfo> {
	@Override
	public Class<FeedInfo> getMapperClass() {
		return FeedInfo.class;
	}

	public FeedInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		FeedInfo o = new FeedInfo();
		o.setFeedId(rs.getLong("feedid"));
		o.setFeedType(rs.getByte("feedtype"));
		o.setObjId(rs.getLong("objid"));
		o.setObj2Id(rs.getLong("obj2id"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
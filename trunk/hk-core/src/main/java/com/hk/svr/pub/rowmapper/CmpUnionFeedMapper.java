package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUnionFeed;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUnionFeedMapper extends HkRowMapper<CmpUnionFeed> {
	@Override
	public Class<CmpUnionFeed> getMapperClass() {
		return CmpUnionFeed.class;
	}

	public CmpUnionFeed mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpUnionFeed o = new CmpUnionFeed();
		o.setFeedId(rs.getLong("feedid"));
		o.setUid(rs.getLong("uid"));
		o.setObjId(rs.getLong("objid"));
		o.setFeedflg(rs.getInt("feedflg"));
		o.setData(rs.getString("data"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
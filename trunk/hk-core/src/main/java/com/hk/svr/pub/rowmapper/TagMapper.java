package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Tag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class TagMapper extends HkRowMapper<Tag> {
	@Override
	public Class<Tag> getMapperClass() {
		return Tag.class;
	}

	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tag o = new Tag();
		o.setTagId(rs.getLong("tagid"));
		o.setName(rs.getString("name"));
		o.setLabaCount(rs.getInt("labacount"));
		o.setUserCount(rs.getInt("usercount"));
		o.setHot(rs.getInt("hot"));
		o.setUpdateTime(rs.getTimestamp("updatetime"));
		return o;
	}
}
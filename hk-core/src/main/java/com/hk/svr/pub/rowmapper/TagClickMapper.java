package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.TagClick;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class TagClickMapper extends HkRowMapper<TagClick> {
	@Override
	public Class<TagClick> getMapperClass() {
		return TagClick.class;
	}

	public TagClick mapRow(ResultSet rs, int rowNum) throws SQLException {
		TagClick o = new TagClick();
		o.setTagId(rs.getLong("tagid"));
		o.setUserId(rs.getLong("userid"));
		o.setPcount(rs.getInt("pcount"));
		return o;
	}
}
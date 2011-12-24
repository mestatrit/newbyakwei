package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.TagLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class TagLabaMapper extends HkRowMapper<TagLaba> {
	@Override
	public Class<TagLaba> getMapperClass() {
		return TagLaba.class;
	}

	public TagLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		TagLaba o = new TagLaba();
		o.setTagId(rs.getLong("tagid"));
		o.setLabaId(rs.getLong("labaid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
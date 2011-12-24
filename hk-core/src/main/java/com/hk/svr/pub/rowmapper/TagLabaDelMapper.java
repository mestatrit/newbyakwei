package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.TagLabaDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class TagLabaDelMapper extends HkRowMapper<TagLabaDel> {
	@Override
	public Class<TagLabaDel> getMapperClass() {
		return TagLabaDel.class;
	}

	public TagLabaDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		TagLabaDel o = new TagLabaDel();
		o.setTagId(rs.getLong("tagid"));
		o.setLabaId(rs.getLong("labaid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
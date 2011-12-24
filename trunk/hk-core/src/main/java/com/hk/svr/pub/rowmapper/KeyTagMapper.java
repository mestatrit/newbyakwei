package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.KeyTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class KeyTagMapper extends HkRowMapper<KeyTag> {
	@Override
	public Class<KeyTag> getMapperClass() {
		return KeyTag.class;
	}

	public KeyTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		KeyTag o = new KeyTag();
		o.setTagId(rs.getLong("tagid"));
		o.setName(rs.getString("name"));
		o.setSearchCount(rs.getInt("searchCount"));
		return o;
	}
}
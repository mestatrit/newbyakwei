package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.LabaTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaTagMapper extends HkRowMapper<LabaTag> {
	@Override
	public Class<LabaTag> getMapperClass() {
		return LabaTag.class;
	}

	public LabaTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		LabaTag o = new LabaTag();
		o.setLabaId(rs.getLong("labaid"));
		o.setTagId(rs.getLong("tagid"));
		o.setAccessional(rs.getByte("accessional"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
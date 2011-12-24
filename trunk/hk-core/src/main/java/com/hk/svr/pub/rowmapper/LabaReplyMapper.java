package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.LabaReply;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaReplyMapper extends HkRowMapper<LabaReply> {
	@Override
	public Class<LabaReply> getMapperClass() {
		return LabaReply.class;
	}

	public LabaReply mapRow(ResultSet rs, int rowNum) throws SQLException {
		LabaReply o = new LabaReply();
		o.setLabaId(rs.getLong("labaid"));
		o.setReplyLabaId(rs.getLong("replylabaid"));
		return o;
	}
}
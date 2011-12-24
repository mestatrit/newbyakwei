package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.LabaRootReply;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaRootReplyMapper extends HkRowMapper<LabaRootReply> {
	@Override
	public Class<LabaRootReply> getMapperClass() {
		return LabaRootReply.class;
	}

	public LabaRootReply mapRow(ResultSet rs, int rowNum) throws SQLException {
		LabaRootReply o = new LabaRootReply();
		o.setLabaId(rs.getLong("labaid"));
		o.setReplyLabaId(rs.getLong("replylabaid"));
		return o;
	}
}
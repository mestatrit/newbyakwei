package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.LabaSeq;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaSeqMapper extends HkRowMapper<LabaSeq> {
	@Override
	public Class<LabaSeq> getMapperClass() {
		return LabaSeq.class;
	}

	public LabaSeq mapRow(ResultSet rs, int rowNum) throws SQLException {
		LabaSeq o = new LabaSeq();
		o.setLabaId(rs.getLong("labaid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
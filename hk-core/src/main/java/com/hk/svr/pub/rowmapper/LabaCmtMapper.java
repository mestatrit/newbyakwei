package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.LabaCmt;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaCmtMapper extends HkRowMapper<LabaCmt> {
	@Override
	public Class<LabaCmt> getMapperClass() {
		return LabaCmt.class;
	}

	public LabaCmt mapRow(ResultSet rs, int rowNum) throws SQLException {
		LabaCmt o = new LabaCmt();
		o.setCmtId(rs.getLong("cmtid"));
		o.setLabaId(rs.getLong("labaid"));
		o.setUserId(rs.getLong("userid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
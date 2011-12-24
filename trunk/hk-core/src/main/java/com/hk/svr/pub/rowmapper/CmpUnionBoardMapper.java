package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUnionBoard;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUnionBoardMapper extends HkRowMapper<CmpUnionBoard> {
	@Override
	public Class<CmpUnionBoard> getMapperClass() {
		return CmpUnionBoard.class;
	}

	public CmpUnionBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpUnionBoard o = new CmpUnionBoard();
		o.setBoardId(rs.getLong("boardid"));
		o.setUid(rs.getLong("uid"));
		o.setTitle(rs.getString("title"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
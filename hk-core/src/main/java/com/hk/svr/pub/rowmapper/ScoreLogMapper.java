package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.ScoreLog;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ScoreLogMapper extends HkRowMapper<ScoreLog> {
	@Override
	public Class<ScoreLog> getMapperClass() {
		return ScoreLog.class;
	}

	public ScoreLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		ScoreLog o = new ScoreLog();
		o.setLogId(rs.getLong("logid"));
		o.setUserId(rs.getLong("userid"));
		o.setScoretype(rs.getInt("scoretype"));
		o.setAddcount(rs.getInt("addcount"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setObjId(rs.getLong("objid"));
		return o;
	}
}
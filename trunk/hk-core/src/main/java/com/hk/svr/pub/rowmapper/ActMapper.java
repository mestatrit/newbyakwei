package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Act;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ActMapper extends HkRowMapper<Act> {
	@Override
	public Class<Act> getMapperClass() {
		return Act.class;
	}

	public Act mapRow(ResultSet rs, int rowNum) throws SQLException {
		Act o = new Act();
		o.setActId(rs.getLong("actid"));
		o.setUserId(rs.getLong("userid"));
		o.setName(rs.getString("name"));
		o.setBeginTime(rs.getTimestamp("begintime"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setAddr(rs.getString("addr"));
		o.setIntro(rs.getString("intro"));
		o.setNeedCheck(rs.getByte("needcheck"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setPortId(rs.getLong("portid"));
		o.setActSysNumId(rs.getLong("actsysnumid"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Information;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class InformationMapper extends HkRowMapper<Information> {
	@Override
	public Class<Information> getMapperClass() {
		return Information.class;
	}

	public Information mapRow(ResultSet rs, int rowNum) throws SQLException {
		Information o = new Information();
		o.setInfoId(rs.getLong("infoid"));
		o.setPortId(rs.getLong("portid"));
		o.setUserId(rs.getLong("userid"));
		o.setTag(rs.getString("tag"));
		o.setName(rs.getString("name"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setUseStatus(rs.getByte("usestatus"));
		o.setBeginTime(rs.getTimestamp("begintime"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setIntro(rs.getString("intro"));
		return o;
	}
}
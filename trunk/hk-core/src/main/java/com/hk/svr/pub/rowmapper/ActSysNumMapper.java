package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.ActSysNum;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ActSysNumMapper extends HkRowMapper<ActSysNum> {
	@Override
	public Class<ActSysNum> getMapperClass() {
		return ActSysNum.class;
	}

	public ActSysNum mapRow(ResultSet rs, int rowNum) throws SQLException {
		ActSysNum o = new ActSysNum();
		o.setSysId(rs.getInt("sysid"));
		o.setSysnum(rs.getString("sysnum"));
		o.setSysstatus(rs.getByte("sysstatus"));
		o.setOverTime(rs.getTimestamp("overtime"));
		o.setActId(rs.getLong("actid"));
		o.setUsetype(rs.getByte("usetype"));
		return o;
	}
}
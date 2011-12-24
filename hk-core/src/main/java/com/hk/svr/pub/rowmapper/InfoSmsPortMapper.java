package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.InfoSmsPort;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class InfoSmsPortMapper extends HkRowMapper<InfoSmsPort> {
	@Override
	public Class<InfoSmsPort> getMapperClass() {
		return InfoSmsPort.class;
	}

	public InfoSmsPort mapRow(ResultSet rs, int rowNum) throws SQLException {
		InfoSmsPort o = new InfoSmsPort();
		o.setPortId(rs.getLong("portid"));
		o.setPortNumber(rs.getString("portnumber"));
		o.setUserId(rs.getLong("userid"));
		o.setOverTime(rs.getTimestamp("overtime"));
		o.setLevel(rs.getByte("level"));
		o.setUsetype(rs.getByte("usetype"));
		o.setActId(rs.getLong("actid"));
		return o;
	}
}
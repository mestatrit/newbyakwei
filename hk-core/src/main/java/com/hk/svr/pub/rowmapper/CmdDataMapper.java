package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmdData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmdDataMapper extends HkRowMapper<CmdData> {
	@Override
	public Class<CmdData> getMapperClass() {
		return CmdData.class;
	}

	public CmdData mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmdData o = new CmdData();
		o.setCmdId(rs.getLong("cmdid"));
		o.setName(rs.getString("name"));
		o.setOid(rs.getLong("oid"));
		o.setOtype(rs.getInt("otype"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setEndflg(rs.getByte("endflg"));
		return o;
	}
}
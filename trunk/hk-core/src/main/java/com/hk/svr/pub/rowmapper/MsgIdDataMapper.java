package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.MsgIdData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class MsgIdDataMapper extends HkRowMapper<MsgIdData> {
	@Override
	public Class<MsgIdData> getMapperClass() {
		return MsgIdData.class;
	}

	public MsgIdData mapRow(ResultSet rs, int rowNum) throws SQLException {
		MsgIdData o = new MsgIdData();
		o.setSysId(rs.getInt("sysid"));
		o.setMsgId(rs.getInt("msgid"));
		return o;
	}
}
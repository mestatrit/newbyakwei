package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkObjOrderHkbLog;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkObjOrderHkbLogMapper extends HkRowMapper<HkObjOrderHkbLog> {
	@Override
	public Class<HkObjOrderHkbLog> getMapperClass() {
		return HkObjOrderHkbLog.class;
	}

	public HkObjOrderHkbLog mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		HkObjOrderHkbLog o = new HkObjOrderHkbLog();
		o.setOid(rs.getLong("oid"));
		o.setAddflg(rs.getByte("addflg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setHkb(rs.getInt("hkb"));
		o.setHkObjId(rs.getLong("hkobjid"));
		return o;
	}
}

package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpGroupSmsPort;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpGroupSmsPortMapper extends HkRowMapper<CmpGroupSmsPort> {
	@Override
	public Class<CmpGroupSmsPort> getMapperClass() {
		return CmpGroupSmsPort.class;
	}

	public CmpGroupSmsPort mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpGroupSmsPort o = new CmpGroupSmsPort();
		o.setOid(rs.getLong("oid"));
		o.setCmpgroupId(rs.getLong("cmpgroupid"));
		o.setPort(rs.getString("port"));
		return o;
	}
}
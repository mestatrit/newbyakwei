package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpSmsPort;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpSmsPortMapper extends HkRowMapper<CmpSmsPort> {
	@Override
	public Class<CmpSmsPort> getMapperClass() {
		return CmpSmsPort.class;
	}

	public CmpSmsPort mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpSmsPort o = new CmpSmsPort();
		o.setPortId(rs.getLong("portid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setPort(rs.getString("port"));
		return o;
	}
}
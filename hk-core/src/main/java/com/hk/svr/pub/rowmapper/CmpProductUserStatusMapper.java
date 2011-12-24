package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProductUserStatus;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductUserStatusMapper extends
		HkRowMapper<CmpProductUserStatus> {
	@Override
	public Class<CmpProductUserStatus> getMapperClass() {
		return CmpProductUserStatus.class;
	}

	public CmpProductUserStatus mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpProductUserStatus o = new CmpProductUserStatus();
		o.setSysId(rs.getLong("sysid"));
		o.setProductId(rs.getLong("productid"));
		o.setUserId(rs.getLong("userid"));
		o.setUserStatus(rs.getByte("userstatus"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
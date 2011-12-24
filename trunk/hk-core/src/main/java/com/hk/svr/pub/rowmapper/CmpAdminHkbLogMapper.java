package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpAdminHkbLog;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpAdminHkbLogMapper extends HkRowMapper<CmpAdminHkbLog> {
	@Override
	public Class<CmpAdminHkbLog> getMapperClass() {
		return CmpAdminHkbLog.class;
	}

	public CmpAdminHkbLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpAdminHkbLog o = new CmpAdminHkbLog();
		o.setSysId(rs.getLong("sysid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setMoney(rs.getDouble("money"));
		o.setAddflg(rs.getByte("addflg"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setRemark(rs.getString("remark"));
		o.setAddCount(rs.getInt("addcount"));
		return o;
	}
}
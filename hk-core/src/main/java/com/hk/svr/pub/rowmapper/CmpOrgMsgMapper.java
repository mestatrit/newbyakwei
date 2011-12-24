package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOrgMsg;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOrgMsgMapper extends HkRowMapper<CmpOrgMsg> {

	@Override
	public Class<CmpOrgMsg> getMapperClass() {
		return CmpOrgMsg.class;
	}

	public CmpOrgMsg mapRow(ResultSet rs, int arg1) throws SQLException {
		CmpOrgMsg o = new CmpOrgMsg();
		o.setOrgId(rs.getLong("orgid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setOid(rs.getLong("oid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setIp(rs.getString("ip"));
		o.setName(rs.getString("name"));
		o.setTel(rs.getString("tel"));
		o.setIm(rs.getString("im"));
		o.setEmail(rs.getString("email"));
		return o;
	}
}
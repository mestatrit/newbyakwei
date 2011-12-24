package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpMsg;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpMsgMapper extends HkRowMapper<CmpMsg> {

	@Override
	public Class<CmpMsg> getMapperClass() {
		return CmpMsg.class;
	}

	public CmpMsg mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpMsg o = new CmpMsg();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setTel(rs.getString("tel"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setIp(rs.getString("ip"));
		o.setCmppink(rs.getByte("cmppink"));
		o.setCmppinkTime(rs.getTimestamp("cmppinktime"));
		return o;
	}
}
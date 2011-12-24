package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpMemberMoneyLog;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpMemberMoneyLogMapper extends HkRowMapper<CmpMemberMoneyLog> {
	@Override
	public Class<CmpMemberMoneyLog> getMapperClass() {
		return CmpMemberMoneyLog.class;
	}

	public CmpMemberMoneyLog mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpMemberMoneyLog o = new CmpMemberMoneyLog();
		o.setOid(rs.getLong("oid"));
		o.setAddflg(rs.getByte("addflg"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setMemberId(rs.getLong("memberid"));
		o.setMoney(rs.getDouble("money"));
		o.setOldMoney(rs.getDouble("oldmoney"));
		return o;
	}
}
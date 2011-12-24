package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpAct;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActMapper extends HkRowMapper<CmpAct> {
	@Override
	public Class<CmpAct> getMapperClass() {
		return CmpAct.class;
	}

	public CmpAct mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpAct o = new CmpAct();
		o.setActId(rs.getLong("actid"));
		o.setName(rs.getString("name"));
		o.setKindId(rs.getLong("kindid"));
		o.setActKey(rs.getString("actkey"));
		o.setIntro(rs.getString("intro"));
		o.setBeginTime(rs.getTimestamp("begintime"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setActStatus(rs.getByte("actstatus"));
		o.setSpintro(rs.getString("spintro"));
		o.setUserNeedCheckflg(rs.getByte("userneedcheckflg"));
		o.setPcityId(rs.getInt("pcityid"));
		o.setMemberLimitflg(rs.getByte("memberlimitflg"));
		o.setActCost(rs.getDouble("actcost"));
		o.setUserLimitCount(rs.getInt("userlimitcount"));
		o.setAddr(rs.getString("addr"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUid(rs.getLong("uid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
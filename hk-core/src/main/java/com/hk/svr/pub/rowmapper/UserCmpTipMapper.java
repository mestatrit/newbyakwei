package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserCmpTip;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserCmpTipMapper extends HkRowMapper<UserCmpTip> {
	@Override
	public Class<UserCmpTip> getMapperClass() {
		return UserCmpTip.class;
	}

	public UserCmpTip mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCmpTip o = new UserCmpTip();
		o.setOid(rs.getLong("oid"));
		o.setTipId(rs.getLong("tipid"));
		o.setUserId(rs.getLong("userid"));
		o.setDoneflg(rs.getByte("doneflg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setPcityId(rs.getInt("pcityid"));
		o.setData(rs.getString("data"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
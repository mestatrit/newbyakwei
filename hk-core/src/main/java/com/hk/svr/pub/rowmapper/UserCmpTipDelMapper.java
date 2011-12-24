package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserCmpTipDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserCmpTipDelMapper extends HkRowMapper<UserCmpTipDel> {

	@Override
	public Class<UserCmpTipDel> getMapperClass() {
		return UserCmpTipDel.class;
	}

	public UserCmpTipDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCmpTipDel o = new UserCmpTipDel();
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
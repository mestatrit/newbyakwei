package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserCoupon;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserCouponMapper extends HkRowMapper<UserCoupon> {

	@Override
	public Class<UserCoupon> getMapperClass() {
		return UserCoupon.class;
	}

	public UserCoupon mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCoupon o = new UserCoupon();
		o.setMcode(rs.getString("mcode"));
		o.setCouponId(rs.getLong("couponid"));
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setUseflg(rs.getByte("useflg"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.Coupon;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CouponMapper extends HkRowMapper<Coupon> {

	@Override
	public Class<Coupon> getMapperClass() {
		return Coupon.class;
	}

	public Coupon mapRow(ResultSet rs, int rowNum) throws SQLException {
		Coupon o = new Coupon();
		o.setCouponId(rs.getLong("couponid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setContent(rs.getString("content"));
		o.setRemark(rs.getString("remark"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setDcount(rs.getInt("dcount"));
		o.setOverdueflg(rs.getByte("overdueflg"));
		o.setPicpath(rs.getString("picpath"));
		o.setAmount(rs.getInt("amount"));
		o.setUseflg(rs.getByte("useflg"));
		o.setUid(rs.getLong("uid"));
		o.setData(rs.getString("data"));
		o.setUserId(rs.getLong("userid"));
		o.setCityId(rs.getInt("cityid"));
		o.setCodedata(rs.getString("codedata"));
		o.setCmppink(rs.getByte("cmppink"));
		o.setCmppinkTime(rs.getTimestamp("cmppinktime"));
		return o;
	}
}
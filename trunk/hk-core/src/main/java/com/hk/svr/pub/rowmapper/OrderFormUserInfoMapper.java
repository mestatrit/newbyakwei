package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.OrderFormUserInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class OrderFormUserInfoMapper extends HkRowMapper<OrderFormUserInfo> {
	@Override
	public Class<OrderFormUserInfo> getMapperClass() {
		return OrderFormUserInfo.class;
	}

	public OrderFormUserInfo mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		OrderFormUserInfo o = new OrderFormUserInfo();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setName(rs.getString("name"));
		o.setMobile(rs.getString("mobile"));
		o.setTitle(rs.getString("title"));
		o.setDefflg(rs.getByte("defflg"));
		o.setTel(rs.getString("tel"));
		o.setEmail(rs.getString("email"));
		return o;
	}
}
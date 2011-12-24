package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.OrderForm;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class OrderFormMapper extends HkRowMapper<OrderForm> {
	@Override
	public Class<OrderForm> getMapperClass() {
		return OrderForm.class;
	}

	public OrderForm mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderForm o = new OrderForm();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setPrice(rs.getDouble("price"));
		o.setOrderStatus(rs.getByte("orderstatus"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setCheckoutflg(rs.getByte("checkoutflg"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setMobile(rs.getString("mobile"));
		o.setName(rs.getString("name"));
		o.setOptflg(rs.getByte("optflg"));
		o.setOrderTime(rs.getTimestamp("ordertime"));
		o.setContent(rs.getString("content"));
		o.setTotalCount(rs.getInt("totalcount"));
		o.setTel(rs.getString("tel"));
		o.setTabledata(rs.getString("tabledata"));
		return o;
	}
}
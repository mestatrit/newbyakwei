package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.OrderItem;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class OrderItemMapper extends HkRowMapper<OrderItem> {
	@Override
	public Class<OrderItem> getMapperClass() {
		return OrderItem.class;
	}

	public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrderItem o = new OrderItem();
		o.setItemId(rs.getLong("itemid"));
		o.setPcount(rs.getInt("pcount"));
		o.setPrice(rs.getDouble("price"));
		o.setProductId(rs.getInt("productid"));
		o.setName(rs.getString("name"));
		o.setRebate(rs.getDouble("rebate"));
		o.setOrderId(rs.getLong("orderid"));
		o.setUptime(rs.getTimestamp("uptime"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.Laba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaMapper extends HkRowMapper<Laba> {
	@Override
	public Class<Laba> getMapperClass() {
		return Laba.class;
	}

	public Laba mapRow(ResultSet rs, int rowNum) throws SQLException {
		Laba o = new Laba();
		o.setLabaId(rs.getLong("labaid"));
		o.setContent(rs.getString("content"));
		o.setUserId(rs.getLong("userid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setReplyCount(rs.getInt("replycount"));
		o.setSendFrom(rs.getInt("sendfrom"));
		o.setIp(rs.getString("ip"));
		o.setCityId(rs.getInt("cityid"));
		o.setRangeId(rs.getInt("rangeid"));
		o.setRefLabaId(rs.getLong("reflabaid"));
		o.setRefcount(rs.getInt("refcount"));
		o.setLongContent(rs.getString("longcontent"));
		o.setProductId(rs.getInt("productid"));
		o.setMainLabaId(rs.getLong("mainlabaid"));
		return o;
	}
}
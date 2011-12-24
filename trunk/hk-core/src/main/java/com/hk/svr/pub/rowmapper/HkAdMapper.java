package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.HkAd;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkAdMapper extends HkRowMapper<HkAd> {

	@Override
	public Class<HkAd> getMapperClass() {
		return HkAd.class;
	}

	public HkAd mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkAd o = new HkAd();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setShowflg(rs.getByte("showflg"));
		o.setData(rs.getString("data"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setExpendflg(rs.getByte("expendflg"));
		o.setViewCount(rs.getInt("viewcount"));
		o.setTotalViewCount(rs.getInt("totalviewcount"));
		o.setName(rs.getString("name"));
		o.setUseflg(rs.getByte("useflg"));
		o.setHref(rs.getString("href"));
		o.setCityId(rs.getInt("cityid"));
		return o;
	}
}
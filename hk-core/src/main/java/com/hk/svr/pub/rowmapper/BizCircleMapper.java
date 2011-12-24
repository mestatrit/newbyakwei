package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.BizCircle;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class BizCircleMapper extends HkRowMapper<BizCircle> {
	@Override
	public Class<BizCircle> getMapperClass() {
		return BizCircle.class;
	}

	public BizCircle mapRow(ResultSet rs, int rowNum) throws SQLException {
		BizCircle o = new BizCircle();
		o.setCircleId(rs.getInt("circleid"));
		o.setCityId(rs.getInt("cityid"));
		o.setName(rs.getString("name"));
		o.setProvinceId(rs.getInt("provinceid"));
		o.setCmpCount(rs.getInt("cmpcount"));
		return o;
	}
}
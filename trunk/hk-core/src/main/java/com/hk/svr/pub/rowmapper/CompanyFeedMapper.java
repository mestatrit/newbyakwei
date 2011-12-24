package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyFeed;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyFeedMapper extends HkRowMapper<CompanyFeed> {
	@Override
	public Class<CompanyFeed> getMapperClass() {
		return CompanyFeed.class;
	}

	public CompanyFeed mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyFeed o = new CompanyFeed();
		o.setFeedId(rs.getLong("feedid"));
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setCityId(rs.getInt("cityid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setRangeId(rs.getInt("rangeid"));
		return o;
	}
}
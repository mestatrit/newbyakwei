package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.BizCircleRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class BizCircleRefMapper extends HkRowMapper<BizCircleRef> {
	@Override
	public Class<BizCircleRef> getMapperClass() {
		return BizCircleRef.class;
	}

	public BizCircleRef mapRow(ResultSet rs, int rowNum) throws SQLException {
		BizCircleRef o = new BizCircleRef();
		o.setCircleId(rs.getInt("circleid"));
		o.setCompanyId(rs.getInt("companyid"));
		return o;
	}
}
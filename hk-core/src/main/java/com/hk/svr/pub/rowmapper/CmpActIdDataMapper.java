package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActIdData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActIdDataMapper extends HkRowMapper<CmpActIdData> {
	@Override
	public Class<CmpActIdData> getMapperClass() {
		return CmpActIdData.class;
	}

	public CmpActIdData mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActIdData o = new CmpActIdData();
		o.setActId(rs.getLong("actid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
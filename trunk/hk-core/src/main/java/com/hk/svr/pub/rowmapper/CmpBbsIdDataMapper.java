package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpBbsIdData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBbsIdDataMapper extends HkRowMapper<CmpBbsIdData> {

	@Override
	public Class<CmpBbsIdData> getMapperClass() {
		return CmpBbsIdData.class;
	}

	public CmpBbsIdData mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpBbsIdData o = new CmpBbsIdData();
		o.setBbsId(rs.getLong("bbsid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
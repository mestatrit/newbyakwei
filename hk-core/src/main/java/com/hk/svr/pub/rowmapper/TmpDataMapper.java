package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.TmpData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class TmpDataMapper extends HkRowMapper<TmpData> {

	@Override
	public Class<TmpData> getMapperClass() {
		return TmpData.class;
	}

	public TmpData mapRow(ResultSet rs, int rowNum) throws SQLException {
		TmpData o = new TmpData();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setDatatype(rs.getByte("datatype"));
		o.setData(rs.getString("data"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
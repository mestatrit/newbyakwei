package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.RegCode;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class RegCodeMapper extends HkRowMapper<RegCode> {
	@Override
	public Class<RegCode> getMapperClass() {
		return RegCode.class;
	}

	public RegCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		RegCode o = new RegCode();
		o.setCodeId(rs.getLong("codeid"));
		o.setObjId(rs.getLong("objid"));
		o.setObjType(rs.getByte("objtype"));
		o.setName(rs.getString("name"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
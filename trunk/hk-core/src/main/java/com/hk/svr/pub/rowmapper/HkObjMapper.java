package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkObj;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkObjMapper extends HkRowMapper<HkObj> {
	@Override
	public Class<HkObj> getMapperClass() {
		return HkObj.class;
	}

	public HkObj mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkObj o = new HkObj();
		o.setObjId(rs.getLong("objid"));
		o.setName(rs.getString("name"));
		o.setKindId(rs.getInt("kindid"));
		o.setCheckflg(rs.getByte("checkflg"));
		return o;
	}
}
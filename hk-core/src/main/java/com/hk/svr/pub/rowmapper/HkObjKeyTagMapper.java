package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkObjKeyTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkObjKeyTagMapper extends HkRowMapper<HkObjKeyTag> {
	@Override
	public Class<HkObjKeyTag> getMapperClass() {
		return HkObjKeyTag.class;
	}

	public HkObjKeyTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkObjKeyTag o = new HkObjKeyTag();
		o.setOid(rs.getLong("oid"));
		o.setTagId(rs.getLong("tagid"));
		o.setHkObjId(rs.getLong("hkobjid"));
		return o;
	}
}
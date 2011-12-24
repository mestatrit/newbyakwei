package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkObjKeyTagOrderDef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkObjKeyTagOrderDefMapper extends HkRowMapper<HkObjKeyTagOrderDef> {
	@Override
	public Class<HkObjKeyTagOrderDef> getMapperClass() {
		return HkObjKeyTagOrderDef.class;
	}

	public HkObjKeyTagOrderDef mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		HkObjKeyTagOrderDef o = new HkObjKeyTagOrderDef();
		o.setOid(rs.getLong("oid"));
		o.setTagId(rs.getLong("tagid"));
		o.setCityId(rs.getInt("cityid"));
		o.setMoney(rs.getInt("money"));
		return o;
	}
}
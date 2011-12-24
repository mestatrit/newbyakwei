package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkObjOrderDef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkObjOrderDefMapper extends HkRowMapper<HkObjOrderDef> {
	@Override
	public Class<HkObjOrderDef> getMapperClass() {
		return HkObjOrderDef.class;
	}

	public HkObjOrderDef mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkObjOrderDef o = new HkObjOrderDef();
		o.setOid(rs.getInt("oid"));
		o.setKind(rs.getByte("kind"));
		o.setKindId(rs.getInt("kindid"));
		o.setCityId(rs.getInt("cityid"));
		o.setMoney(rs.getInt("money"));
		return o;
	}
}
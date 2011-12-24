package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkObjOrder;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkObjOrderMapper extends HkRowMapper<HkObjOrder> {
	@Override
	public Class<HkObjOrder> getMapperClass() {
		return HkObjOrder.class;
	}

	public HkObjOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkObjOrder o = new HkObjOrder();
		o.setOid(rs.getLong("oid"));
		o.setKind(rs.getByte("kind"));
		o.setHkObjId(rs.getLong("hkobjid"));
		o.setStopflg(rs.getByte("stopflg"));
		o.setCityId(rs.getInt("cityid"));
		o.setMoney(rs.getInt("money"));
		o.setUtime(rs.getTimestamp("utime"));
		o.setPday(rs.getInt("pday"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
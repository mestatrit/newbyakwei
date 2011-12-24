package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkObjKeyTagOrder;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkObjKeyTagOrderMapper extends HkRowMapper<HkObjKeyTagOrder> {
	@Override
	public Class<HkObjKeyTagOrder> getMapperClass() {
		return HkObjKeyTagOrder.class;
	}

	public HkObjKeyTagOrder mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		HkObjKeyTagOrder o = new HkObjKeyTagOrder();
		o.setOid(rs.getLong("oid"));
		o.setTagId(rs.getLong("tagid"));
		o.setHkObjId(rs.getLong("hkobjid"));
		o.setCityId(rs.getInt("cityid"));
		o.setMoney(rs.getInt("money"));
		o.setStopflg(rs.getByte("stopflg"));
		o.setUtime(rs.getTimestamp("utime"));
		o.setPday(rs.getInt("pday"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
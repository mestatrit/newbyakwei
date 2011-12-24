package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.BombLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class BombLabaMapper extends HkRowMapper<BombLaba> {
	@Override
	public Class<BombLaba> getMapperClass() {
		return BombLaba.class;
	}

	public BombLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		BombLaba o = new BombLaba();
		o.setSysId(rs.getLong("sysid"));
		o.setUserId(rs.getLong("userid"));
		o.setLabaId(rs.getLong("labaid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}
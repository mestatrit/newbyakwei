package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.BoxPrize;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class BoxPrizeMapper extends HkRowMapper<BoxPrize> {

	@Override
	public Class<BoxPrize> getMapperClass() {
		return BoxPrize.class;
	}

	public BoxPrize mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoxPrize o = new BoxPrize();
		o.setPrizeId(rs.getLong("prizeid"));
		o.setBoxId(rs.getLong("boxid"));
		o.setName(rs.getString("name"));
		o.setTip(rs.getString("tip"));
		o.setPcount(rs.getInt("pcount"));
		o.setSignal(rs.getByte("signal"));
		o.setRemain(rs.getInt("remain"));
		o.setPath(rs.getString("path"));
		o.setEid(rs.getLong("eid"));
		return o;
	}
}
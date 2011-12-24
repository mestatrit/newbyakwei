package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Bomber;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class BomberMapper extends HkRowMapper<Bomber> {
	@Override
	public Class<Bomber> getMapperClass() {
		return Bomber.class;
	}

	public Bomber mapRow(ResultSet rs, int rowNum) throws SQLException {
		Bomber o = new Bomber();
		o.setUserId(rs.getLong("userid"));
		o.setBombCount(rs.getInt("bombcount"));
		o.setRemainCount(rs.getInt("remaincount"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setUserLevel(rs.getByte("userlevel"));
		o.setPinkCount(rs.getInt("pinkcount"));
		o.setRemainPinkCount(rs.getInt("remainpinkcount"));
		return o;
	}
}
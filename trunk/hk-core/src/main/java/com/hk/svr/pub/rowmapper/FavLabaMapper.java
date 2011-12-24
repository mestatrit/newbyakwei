package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.FavLaba;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class FavLabaMapper extends HkRowMapper<FavLaba> {
	@Override
	public Class<FavLaba> getMapperClass() {
		return FavLaba.class;
	}

	public FavLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
		FavLaba o = new FavLaba();
		o.setFavId(rs.getLong("favid"));
		o.setUserId(rs.getLong("userid"));
		o.setLabaId(rs.getLong("labaid"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.MyUserCard;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class MyUserCardMapper extends HkRowMapper<MyUserCard> {
	@Override
	public Class<MyUserCard> getMapperClass() {
		return MyUserCard.class;
	}

	public MyUserCard mapRow(ResultSet rs, int rowNum) throws SQLException {
		MyUserCard o = new MyUserCard();
		o.setUserId(rs.getLong("userid"));
		o.setCardUserId(rs.getLong("carduserid"));
		o.setSysId(rs.getLong("sysid"));
		return o;
	}
}
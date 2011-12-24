package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserBoxPrize;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserBoxPrizeMapper extends HkRowMapper<UserBoxPrize> {

	@Override
	public Class<UserBoxPrize> getMapperClass() {
		return UserBoxPrize.class;
	}

	public UserBoxPrize mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserBoxPrize o = new UserBoxPrize();
		o.setSysId(rs.getLong("sysid"));
		o.setUserId(rs.getLong("userid"));
		o.setBoxId(rs.getLong("boxid"));
		o.setPrizeId(rs.getLong("prizeid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setPrizeNum(rs.getString("prizenum"));
		o.setPrizePwd(rs.getString("prizepwd"));
		o.setDrawflg(rs.getByte("drawflg"));
		o.setDrawTime(rs.getTimestamp("drawtime"));
		return o;
	}
}
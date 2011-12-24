package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.AdminHkb;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class AdminHkbMapper extends HkRowMapper<AdminHkb> {
	@Override
	public Class<AdminHkb> getMapperClass() {
		return AdminHkb.class;
	}

	public AdminHkb mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdminHkb o = new AdminHkb();
		o.setSysId(rs.getLong("sysid"));
		o.setUserId(rs.getLong("userid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setAddCount(rs.getInt("addcount"));
		o.setMoney(rs.getInt("money"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setAddflg(rs.getByte("addflg"));
		return o;
	}
}
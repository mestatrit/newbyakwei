package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.AdminUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class AdminUserMapper extends HkRowMapper<AdminUser> {
	@Override
	public Class<AdminUser> getMapperClass() {
		return AdminUser.class;
	}

	public AdminUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		AdminUser o = new AdminUser();
		o.setUserId(rs.getLong("userid"));
		o.setAdminLevel(rs.getByte("adminlevel"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.VipUser;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class VipUserMapper extends HkRowMapper<VipUser> {
	@Override
	public Class<VipUser> getMapperClass() {
		return VipUser.class;
	}

	public VipUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		VipUser o = new VipUser();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
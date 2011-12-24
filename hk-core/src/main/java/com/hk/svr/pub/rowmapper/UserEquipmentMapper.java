package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserEquipment;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserEquipmentMapper extends HkRowMapper<UserEquipment> {

	@Override
	public Class<UserEquipment> getMapperClass() {
		return UserEquipment.class;
	}

	public UserEquipment mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserEquipment o = new UserEquipment();
		o.setOid(rs.getLong("oid"));
		o.setUserId(rs.getLong("userid"));
		o.setEid(rs.getLong("eid"));
		o.setUseflg(rs.getByte("useflg"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setEnjoyUserId(rs.getLong("enjoyuserid"));
		o.setTouchflg(rs.getByte("touchflg"));
		return o;
	}
}
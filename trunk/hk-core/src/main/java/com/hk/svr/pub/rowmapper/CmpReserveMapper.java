package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpReserve;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpReserveMapper extends HkRowMapper<CmpReserve> {

	@Override
	public Class<CmpReserve> getMapperClass() {
		return CmpReserve.class;
	}

	public CmpReserve mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpReserve o = new CmpReserve();
		o.setReserveId(rs.getLong("reserveid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setActorId(rs.getLong("actorid"));
		o.setReserveStatus(rs.getByte("reservestatus"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setReserveTime(rs.getTimestamp("reservetime"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setUserId(rs.getLong("userid"));
		o.setSvrdata(rs.getString("svrdata"));
		o.setUsername(rs.getString("username"));
		return o;
	}
}
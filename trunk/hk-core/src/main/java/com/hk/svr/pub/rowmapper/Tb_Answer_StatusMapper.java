package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Answer_Status;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Answer_StatusMapper extends HkRowMapper<Tb_Answer_Status> {

	@Override
	public Class<Tb_Answer_Status> getMapperClass() {
		return Tb_Answer_Status.class;
	}

	public Tb_Answer_Status mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		Tb_Answer_Status o = new Tb_Answer_Status();
		o.setOid(rs.getLong("oid"));
		o.setAnsid(rs.getLong("ansid"));
		o.setUserid(rs.getLong("userid"));
		o.setAns_status(rs.getByte("ans_status"));
		o.setAid(rs.getLong("aid"));
		return o;
	}
}
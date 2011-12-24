package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.hk.bean.taobao.Tb_CmdItem;

public class Tb_CmdItemMapper implements ParameterizedRowMapper<Tb_CmdItem> {

	@Override
	public Tb_CmdItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_CmdItem o = new Tb_CmdItem();
		o.setOid(rs.getLong("oid"));
		o.setItemid(rs.getLong("itemid"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		return o;
	}
}
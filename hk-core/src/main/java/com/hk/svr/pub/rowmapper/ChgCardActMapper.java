package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.ChgCardAct;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class ChgCardActMapper extends HkRowMapper<ChgCardAct> {
	@Override
	public Class<ChgCardAct> getMapperClass() {
		return ChgCardAct.class;
	}

	public ChgCardAct mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChgCardAct o = new ChgCardAct();
		o.setActId(rs.getLong("actid"));
		o.setName(rs.getString("name"));
		o.setActStatus(rs.getByte("actstatus"));
		o.setChgflg(rs.getByte("chgflg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setPersistHour(rs.getInt("persisthour"));
		o.setSysnum(rs.getString("sysnum"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
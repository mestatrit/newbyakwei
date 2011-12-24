package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.LabaReplyDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaReplyDelMapper extends HkRowMapper<LabaReplyDel> {
	@Override
	public Class<LabaReplyDel> getMapperClass() {
		return LabaReplyDel.class;
	}

	public LabaReplyDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		LabaReplyDel o = new LabaReplyDel();
		o.setLabaId(rs.getLong("labaid"));
		o.setReplyLabaId(rs.getLong("replylabaid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}
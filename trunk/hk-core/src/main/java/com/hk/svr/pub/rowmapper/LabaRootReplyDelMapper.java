package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.LabaRootReplyDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaRootReplyDelMapper extends HkRowMapper<LabaRootReplyDel> {
	@Override
	public Class<LabaRootReplyDel> getMapperClass() {
		return LabaRootReplyDel.class;
	}

	public LabaRootReplyDel mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		LabaRootReplyDel o = new LabaRootReplyDel();
		o.setLabaId(rs.getLong("labaid"));
		o.setReplyLabaId(rs.getLong("replylabaid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}
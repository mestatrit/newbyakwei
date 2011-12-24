package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.LabaTagDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaTagDelMapper extends HkRowMapper<LabaTagDel> {
	@Override
	public Class<LabaTagDel> getMapperClass() {
		return LabaTagDel.class;
	}

	public LabaTagDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		LabaTagDel o = new LabaTagDel();
		o.setLabaId(rs.getLong("labaid"));
		o.setTagId(rs.getLong("tagid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		o.setAccessional(rs.getByte("accessional"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
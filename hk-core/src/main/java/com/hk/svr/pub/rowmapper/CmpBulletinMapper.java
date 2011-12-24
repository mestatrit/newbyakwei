package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpBulletin;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBulletinMapper extends HkRowMapper<CmpBulletin> {
	@Override
	public Class<CmpBulletin> getMapperClass() {
		return CmpBulletin.class;
	}

	public CmpBulletin mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpBulletin o = new CmpBulletin();
		o.setBulletinId(rs.getInt("bulletinid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setTitle(rs.getString("title"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
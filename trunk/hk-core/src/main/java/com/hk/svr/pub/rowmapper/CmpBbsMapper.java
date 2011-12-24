package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpBbs;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBbsMapper extends HkRowMapper<CmpBbs> {

	@Override
	public Class<CmpBbs> getMapperClass() {
		return CmpBbs.class;
	}

	public CmpBbs mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpBbs o = new CmpBbs();
		o.setBbsId(rs.getLong("bbsid"));
		o.setKindId(rs.getLong("kindid"));
		o.setUserId(rs.getLong("userid"));
		o.setTitle(rs.getString("title"));
		o.setLastReplyUserId(rs.getLong("lastreplyuserid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setLastReplyTime(rs.getTimestamp("lastreplytime"));
		o.setPhotoId(rs.getLong("photoid"));
		o.setPicpath(rs.getString("picpath"));
		o.setReplyCount(rs.getInt("replycount"));
		o.setViewCount(rs.getInt("viewcount"));
		o.setIp(rs.getString("ip"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setCmppink(rs.getByte("cmppink"));
		o.setCmppinkTime(rs.getTimestamp("cmppinktime"));
		return o;
	}
}
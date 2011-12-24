package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpBbsDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBbsDelMapper extends HkRowMapper<CmpBbsDel> {

	@Override
	public Class<CmpBbsDel> getMapperClass() {
		return CmpBbsDel.class;
	}

	public CmpBbsDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpBbsDel o = new CmpBbsDel();
		o.setBbsId(rs.getLong("bbsid"));
		o.setKindId(rs.getLong("kindid"));
		o.setUserId(rs.getLong("userid"));
		o.setTitle(rs.getString("title"));
		o.setLastReplyUserId(rs.getLong("lastreplyuserid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setLastReplyTime(rs.getTimestamp("lastreplytime"));
		o.setPicpath(rs.getString("picpath"));
		o.setReplyCount(rs.getInt("replycount"));
		o.setViewCount(rs.getInt("viewcount"));
		o.setContent(rs.getString("content"));
		o.setIp(rs.getString("ip"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getTimestamp("optime"));
		o.setPhotoId(rs.getLong("photoid"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
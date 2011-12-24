package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpBbsReply;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBbsReplyMapper extends HkRowMapper<CmpBbsReply> {

	@Override
	public Class<CmpBbsReply> getMapperClass() {
		return CmpBbsReply.class;
	}

	public CmpBbsReply mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpBbsReply o = new CmpBbsReply();
		o.setReplyId(rs.getLong("replyid"));
		o.setUserId(rs.getLong("userid"));
		o.setBbsId(rs.getLong("bbsid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setPhotoId(rs.getLong("photoid"));
		o.setPicpath(rs.getString("picpath"));
		o.setReplyUserId(rs.getLong("replyuserid"));
		o.setIp(rs.getString("ip"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
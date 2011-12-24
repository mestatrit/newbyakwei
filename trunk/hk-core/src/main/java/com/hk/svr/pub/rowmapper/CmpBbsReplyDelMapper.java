package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpBbsReplyDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBbsReplyDelMapper extends HkRowMapper<CmpBbsReplyDel> {

	@Override
	public Class<CmpBbsReplyDel> getMapperClass() {
		return CmpBbsReplyDel.class;
	}

	public CmpBbsReplyDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpBbsReplyDel o = new CmpBbsReplyDel();
		o.setReplyId(rs.getLong("replyid"));
		o.setUserId(rs.getLong("userid"));
		o.setBbsId(rs.getLong("bbsid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setPhotoId(rs.getLong("photoid"));
		o.setPicpath(rs.getString("picpath"));
		o.setReplyUserId(rs.getLong("replyuserid"));
		o.setIp(rs.getString("ip"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getTimestamp("optime"));
		o.setCompanyId(rs.getLong("companyid"));
		return o;
	}
}
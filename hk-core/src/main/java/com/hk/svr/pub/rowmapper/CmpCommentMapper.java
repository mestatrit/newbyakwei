package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpComment;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpCommentMapper extends HkRowMapper<CmpComment> {
	@Override
	public Class<CmpComment> getMapperClass() {
		return CmpComment.class;
	}

	public CmpComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpComment o = new CmpComment();
		o.setCmtId(rs.getLong("cmtid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setSendfrom(rs.getInt("sendfrom"));
		o.setIp(rs.getString("ip"));
		return o;
	}
}
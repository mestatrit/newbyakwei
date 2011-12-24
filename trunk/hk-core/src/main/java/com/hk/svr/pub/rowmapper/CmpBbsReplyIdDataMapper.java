package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpBbsReplyIdData;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpBbsReplyIdDataMapper extends HkRowMapper<CmpBbsReplyIdData> {

	@Override
	public Class<CmpBbsReplyIdData> getMapperClass() {
		return CmpBbsReplyIdData.class;
	}

	public CmpBbsReplyIdData mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpBbsReplyIdData o = new CmpBbsReplyIdData();
		o.setReplyId(rs.getLong("replyid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.Notice;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class NoticeMapper extends HkRowMapper<Notice> {
	@Override
	public Class<Notice> getMapperClass() {
		return Notice.class;
	}

	public Notice mapRow(ResultSet rs, int rowNum) throws SQLException {
		Notice o = new Notice();
		o.setNoticeId(rs.getLong("noticeid"));
		o.setUserId(rs.getLong("userid"));
		o.setReadflg(rs.getByte("readflg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setNoticeType(rs.getByte("noticetype"));
		o.setData(rs.getString("data"));
		return o;
	}
}

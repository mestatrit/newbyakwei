package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUnionNotice;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUnionNoticeMapper extends HkRowMapper<CmpUnionNotice> {
	@Override
	public Class<CmpUnionNotice> getMapperClass() {
		return CmpUnionNotice.class;
	}

	public CmpUnionNotice mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpUnionNotice o = new CmpUnionNotice();
		o.setNoticeId(rs.getLong("noticeid"));
		o.setUid(rs.getLong("uid"));
		o.setNoticeflg(rs.getInt("noticeflg"));
		o.setObjId(rs.getLong("objid"));
		o.setData(rs.getString("data"));
		o.setReadflg(rs.getByte("readflg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
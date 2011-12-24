package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.PhotoCmt;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class PhotoCmtMapper extends HkRowMapper<PhotoCmt> {
	@Override
	public Class<PhotoCmt> getMapperClass() {
		return PhotoCmt.class;
	}

	public PhotoCmt mapRow(ResultSet rs, int rowNum) throws SQLException {
		PhotoCmt o = new PhotoCmt();
		o.setCmtId(rs.getLong("cmtid"));
		o.setPhotoId(rs.getLong("photoid"));
		o.setUserId(rs.getLong("userid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		return o;
	}
}
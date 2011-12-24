package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Photo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class PhotoMapper extends HkRowMapper<Photo> {
	@Override
	public Class<Photo> getMapperClass() {
		return Photo.class;
	}

	public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Photo o = new Photo();
		o.setPhotoId(rs.getLong("photoid"));
		o.setUserId(rs.getLong("userid"));
		o.setPath(rs.getString("path"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setName(rs.getString("name"));
		return o;
	}
}
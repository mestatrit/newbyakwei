package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CompanyPhoto;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyPhotoMapper extends HkRowMapper<CompanyPhoto> {
	@Override
	public Class<CompanyPhoto> getMapperClass() {
		return CompanyPhoto.class;
	}

	public CompanyPhoto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyPhoto o = new CompanyPhoto();
		o.setPhotoId(rs.getLong("photoid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setPath(rs.getString("path"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setName(rs.getString("name"));
		o.setPinkflg(rs.getByte("pinkflg"));
		o.setVoteCount(rs.getInt("votecount"));
		return o;
	}
}
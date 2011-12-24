package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpPhotoVote;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpPhotoVoteMapper extends HkRowMapper<CmpPhotoVote> {
	@Override
	public Class<CmpPhotoVote> getMapperClass() {
		return CmpPhotoVote.class;
	}

	public CmpPhotoVote mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpPhotoVote o = new CmpPhotoVote();
		o.setPhotoId(rs.getLong("photoid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		return o;
	}
}
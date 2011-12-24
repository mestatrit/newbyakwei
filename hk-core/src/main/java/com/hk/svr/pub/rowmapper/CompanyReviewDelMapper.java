package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CompanyReviewDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyReviewDelMapper extends HkRowMapper<CompanyReviewDel> {
	@Override
	public Class<CompanyReviewDel> getMapperClass() {
		return CompanyReviewDel.class;
	}

	public CompanyReviewDel mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CompanyReviewDel o = new CompanyReviewDel();
		o.setUserId(rs.getLong("userid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setScore(rs.getInt("score"));
		o.setContent(rs.getString("content"));
		o.setLongContent(rs.getString("longcontent"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setLabaId(rs.getLong("labaid"));
		o.setSendFrom(rs.getInt("sendfrom"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		o.setCheckflg(rs.getByte("checkflg"));
		return o;
	}
}
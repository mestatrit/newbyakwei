package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.HkObjArticle;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class HkObjArticleMapper extends HkRowMapper<HkObjArticle> {
	@Override
	public Class<HkObjArticle> getMapperClass() {
		return HkObjArticle.class;
	}

	public HkObjArticle mapRow(ResultSet rs, int rowNum) throws SQLException {
		HkObjArticle o = new HkObjArticle();
		o.setArticleId(rs.getLong("articleid"));
		o.setUserId(rs.getLong("userid"));
		o.setHkObjId(rs.getLong("hkobjid"));
		o.setTitle(rs.getString("title"));
		o.setUrl(rs.getString("url"));
		o.setAuthorflg(rs.getByte("authorflg"));
		o.setCheckflg(rs.getByte("checkflg"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setBlog(rs.getString("blog"));
		o.setTel(rs.getString("tel"));
		o.setEmail(rs.getString("email"));
		o.setAuthor(rs.getString("author"));
		return o;
	}
}
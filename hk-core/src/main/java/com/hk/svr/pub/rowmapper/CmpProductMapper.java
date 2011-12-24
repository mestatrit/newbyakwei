package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpProduct;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpProductMapper extends HkRowMapper<CmpProduct> {

	@Override
	public Class<CmpProduct> getMapperClass() {
		return CmpProduct.class;
	}

	public CmpProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpProduct o = new CmpProduct();
		o.setProductId(rs.getInt("productid"));
		o.setName(rs.getString("name"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setSortId(rs.getInt("sortid"));
		o.setIntro(rs.getString("intro"));
		o.setMoney(rs.getDouble("money"));
		o.setRebate(rs.getDouble("rebate"));
		o.setDelflg(rs.getByte("delflg"));
		o.setHeadPath(rs.getString("headpath"));
		o.setSellStatus(rs.getByte("sellstatus"));
		o.setScore(rs.getInt("score"));
		o.setScoreUserCount(rs.getInt("scoreusercount"));
		o.setReviewCount(rs.getInt("reviewcount"));
		o.setOrderflg(rs.getInt("orderflg"));
		o.setPnum(rs.getString("pnum"));
		o.setShortName(rs.getString("shortname"));
		o.setUid(rs.getLong("uid"));
		o.setCmpUnionKindId(rs.getLong("cmpunionkindid"));
		o.setPcityId(rs.getInt("pcityid"));
		o.setCmppink(rs.getByte("cmppink"));
		o.setCmppinkTime(rs.getTimestamp("cmppinktime"));
		o.setOrderflg(rs.getInt("orderflg"));
		return o;
	}
}
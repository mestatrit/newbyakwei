package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUnion;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUnionMapper extends HkRowMapper<CmpUnion> {
	@Override
	public Class<CmpUnion> getMapperClass() {
		return CmpUnion.class;
	}

	public CmpUnion mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpUnion o = new CmpUnion();
		o.setUid(rs.getLong("uid"));
		o.setName(rs.getString("name"));
		o.setAddr(rs.getString("addr"));
		o.setTraffic(rs.getString("traffic"));
		o.setDomain(rs.getString("domain"));
		o.setWebName(rs.getString("webname"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setIntro(rs.getString("intro"));
		o.setLogo(rs.getString("logo"));
		o.setMarketX(rs.getDouble("marketx"));
		o.setMarketY(rs.getDouble("markety"));
		o.setData(rs.getString("data"));
		o.setPcityId(rs.getInt("pcityid"));
		o.setCmpcreateflg(rs.getByte("cmpcreateflg"));
		o.setUnionStatus(rs.getByte("unionstatus"));
		return o;
	}
}
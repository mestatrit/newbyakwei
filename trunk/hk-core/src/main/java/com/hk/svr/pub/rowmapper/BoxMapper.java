package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.Box;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class BoxMapper extends HkRowMapper<Box> {

	@Override
	public Class<Box> getMapperClass() {
		return Box.class;
	}

	public Box mapRow(ResultSet rs, int rowNum) throws SQLException {
		Box o = new Box();
		o.setBoxId(rs.getLong("boxid"));
		o.setUserId(rs.getLong("userid"));
		o.setName(rs.getString("name"));
		o.setBeginTime(rs.getTimestamp("begintime"));
		o.setEndTime(rs.getTimestamp("endtime"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setBoxKey(rs.getString("boxkey"));
		o.setBoxStatus(rs.getByte("boxstatus"));
		o.setTotalCount(rs.getInt("totalcount"));
		o.setBoxType(rs.getInt("boxtype"));
		o.setOpenCount(rs.getInt("opencount"));
		o.setIntro(rs.getString("intro"));
		o.setPrecount(rs.getInt("precount"));
		o.setPretype(rs.getByte("pretype"));
		o.setOpentype(rs.getByte("opentype"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setCheckflg(rs.getByte("checkflg"));
		o.setUid(rs.getLong("uid"));
		o.setPath(rs.getString("path"));
		o.setCityId(rs.getInt("cityid"));
		o.setPinkflg(rs.getByte("pinkflg"));
		o.setVirtualflg(rs.getByte("virtualflg"));
		o.setOtherPrizeflg(rs.getByte("otherPrizeflg"));
		o.setCmppink(rs.getByte("cmppink"));
		o.setCmppinkTime(rs.getTimestamp("cmppinktime"));
		return o;
	}
}
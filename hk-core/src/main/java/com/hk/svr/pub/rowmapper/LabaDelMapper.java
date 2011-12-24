package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.LabaDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class LabaDelMapper extends HkRowMapper<LabaDel> {
	@Override
	public Class<LabaDel> getMapperClass() {
		return LabaDel.class;
	}

	public LabaDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		LabaDel o = new LabaDel();
		o.setLabaId(rs.getLong("labaid"));
		o.setContent(rs.getString("content"));
		o.setUserId(rs.getLong("userid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setReplyCount(rs.getInt("replycount"));
		o.setSendFrom(rs.getInt("sendfrom"));
		o.setIp(rs.getString("ip"));
		o.setCityId(rs.getInt("cityid"));
		o.setRangeId(rs.getInt("rangeid"));
		o.setRefLabaId(rs.getLong("reflabaid"));
		o.setRefcount(rs.getInt("refcount"));
		o.setLongContent(rs.getString("longcontent"));
		o.setMainLabaId(rs.getLong("mainlabaid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}
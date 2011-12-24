package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.PreBox;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class PreBoxMapper extends HkRowMapper<PreBox> {
	@Override
	public Class<PreBox> getMapperClass() {
		return PreBox.class;
	}

	public PreBox mapRow(ResultSet rs, int rowNum) throws SQLException {
		PreBox o = new PreBox();
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
		return o;
	}
}
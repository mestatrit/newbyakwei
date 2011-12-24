package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.OpenBox;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class OpenBoxMapper extends HkRowMapper<OpenBox> {
	@Override
	public Class<OpenBox> getMapperClass() {
		return OpenBox.class;
	}

	public OpenBox mapRow(ResultSet rs, int rowNum) throws SQLException {
		OpenBox o = new OpenBox();
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
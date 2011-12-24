package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.Badge;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class BadgeMapper extends HkRowMapper<Badge> {
	@Override
	public Class<Badge> getMapperClass() {
		return Badge.class;
	}

	public Badge mapRow(ResultSet rs, int rowNum) throws SQLException {
		Badge o = new Badge();
		o.setBadgeId(rs.getLong("badgeid"));
		o.setPath(rs.getString("path"));
		o.setIntro(rs.getString("intro"));
		o.setRuleData(rs.getString("ruledata"));
		o.setLimitflg(rs.getByte("limitflg"));
		o.setRuleflg(rs.getInt("ruleflg"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setGroupId(rs.getLong("groupid"));
		o.setKindId(rs.getInt("kindid"));
		o.setParentKindId(rs.getInt("parentkindid"));
		o.setStopflg(rs.getByte("stopflg"));
		o.setName(rs.getString("name"));
		return o;
	}
}
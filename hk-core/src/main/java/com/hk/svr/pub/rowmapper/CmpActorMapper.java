package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpActor;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpActorMapper extends HkRowMapper<CmpActor> {

	@Override
	public Class<CmpActor> getMapperClass() {
		return CmpActor.class;
	}

	public CmpActor mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpActor o = new CmpActor();
		o.setActorId(rs.getLong("actorid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setRoleId(rs.getLong("roleid"));
		o.setName(rs.getString("name"));
		o.setActorStatus(rs.getByte("actorstatus"));
		o.setPicPath(rs.getString("picpath"));
		o.setIntro(rs.getString("intro"));
		o.setWorkDay(rs.getString("workday"));
		o.setReserveflg(rs.getByte("reserveflg"));
		o.setGender(rs.getByte("gender"));
		o.setWorkCount(rs.getInt("workcount"));
		o.setScore(rs.getInt("score"));
		o.setScoreUserNum(rs.getInt("scoreusernum"));
		return o;
	}
}
package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.UserOtherInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserOtherInfoMapper extends HkRowMapper<UserOtherInfo> {

	public UserOtherInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserOtherInfo o = new UserOtherInfo();
		o.setUserId(rs.getLong("userid"));
		o.setScore(rs.getInt("score"));
		o.setEmail(rs.getString("email"));
		o.setMobile(rs.getString("mobile"));
		o.setPwdHash(rs.getInt("pwdhash"));
		o.setIntro(rs.getString("intro"));
		o.setValidateEmail(rs.getByte("validateemail"));
		o.setUserStatus(rs.getByte("userstatus"));
		o.setMobileBind(rs.getByte("mobilebind"));
		o.setHkb(rs.getInt("hkb"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setName(rs.getString("name"));
		o.setBirthdayMonth(rs.getInt("birthdaymonth"));
		o.setBirthdayDate(rs.getInt("birthdaydate"));
		o.setCityCode(rs.getString("citycode"));
		o.setPwd(rs.getString("pwd"));
		o.setPrvWeb(rs.getString("prvweb"));
		o.setPoints(rs.getInt("points"));
		return o;
	}

	@Override
	public Class<UserOtherInfo> getMapperClass() {
		return UserOtherInfo.class;
	}
}
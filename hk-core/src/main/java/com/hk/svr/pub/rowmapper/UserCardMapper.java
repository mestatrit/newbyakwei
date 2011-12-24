package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserCard;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserCardMapper extends HkRowMapper<UserCard> {
	@Override
	public Class<UserCard> getMapperClass() {
		return UserCard.class;
	}

	public UserCard mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserCard o = new UserCard();
		o.setUserId(rs.getLong("userid"));
		o.setAnotherMobile(rs.getString("anothermobile"));
		o.setCompleteinfo(rs.getByte("completeinfo"));
		o.setEmail(rs.getString("email"));
		o.setGtalk(rs.getString("gtalk"));
		o.setQq(rs.getString("qq"));
		o.setMsn(rs.getString("msn"));
		o.setSkype(rs.getString("skype"));
		o.setWorkAddr(rs.getString("workaddr"));
		o.setWorkPostcode(rs.getString("workpostcode"));
		o.setHomeAddr(rs.getString("homeaddr"));
		o.setHomePostcode(rs.getString("homepostcode"));
		o.setJobRank(rs.getString("jobrank"));
		o.setIntro(rs.getString("intro"));
		o.setHomeTelphone(rs.getString("hometelphone"));
		o.setWorkPlaceWeb(rs.getString("workplaceweb"));
		o.setWorkplace(rs.getString("workplace"));
		o.setChgflg(rs.getByte("chgflg"));
		o.setName(rs.getString("name"));
		o.setNickName(rs.getString("nickname"));
		return o;
	}
}
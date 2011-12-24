package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.Invite;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class InviteMapper extends HkRowMapper<Invite> {
	@Override
	public Class<Invite> getMapperClass() {
		return Invite.class;
	}

	public Invite mapRow(ResultSet rs, int rowNum) throws SQLException {
		Invite o = new Invite();
		o.setInviteId(rs.getLong("inviteid"));
		o.setUserId(rs.getLong("userid"));
		o.setFriendId(rs.getLong("friendid"));
		o.setEmail(rs.getString("email"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setRegTime(rs.getTimestamp("regtime"));
		o.setInviteCount(rs.getInt("invitecount"));
		o.setUptime(rs.getTimestamp("uptime"));
		o.setInviteType(rs.getByte("invitetype"));
		o.setAddhkbflg(rs.getByte("addhkbflg"));
		return o;
	}
}
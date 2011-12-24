package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.UserNoticeInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class UserNoticeInfoMapper extends HkRowMapper<UserNoticeInfo> {
	@Override
	public Class<UserNoticeInfo> getMapperClass() {
		return UserNoticeInfo.class;
	}

	public UserNoticeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserNoticeInfo o = new UserNoticeInfo();
		o.setUserId(rs.getLong("userid"));
		o.setMsgNotice(rs.getByte("msgnotice"));
		o.setLabaReplyNotice(rs.getByte("labareplynotice"));
		o.setLabaReplySysNotice(rs.getByte("labareplysysnotice"));
		o.setFollowNotice(rs.getByte("follownotice"));
		o.setFollowSysNotice(rs.getByte("followsysnotice"));
		o.setFollowIMNotice(rs.getByte("followimnotice"));
		o.setLabaReplyIMNotice(rs.getByte("labareplyimnotice"));
		o.setUserInLabaSysNotice(rs.getByte("userinlabasysnotice"));
		return o;
	}
}
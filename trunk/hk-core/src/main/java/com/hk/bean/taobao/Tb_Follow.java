package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户好友
 * 
 * @author akwei
 */
@Table(name = "tb_follow")
public class Tb_Follow {

	@Id
	private long oid;

	@Column
	private long userid;

	@Column
	private long friendid;

	private Tb_User friend;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getFriendid() {
		return friendid;
	}

	public void setFriendid(long friendid) {
		this.friendid = friendid;
	}

	public Tb_User getFriend() {
		return friend;
	}

	public void setFriend(Tb_User friend) {
		this.friend = friend;
	}
}
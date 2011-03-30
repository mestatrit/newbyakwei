package bean;

import com.hk.frame.dao.annotation.Column;

public class UserVo {

	@Column("testuser.userid")
	private long userid;

	@Column("testuser.nick")
	private String nick;

	@Column("member.memberid")
	private long memberid;

	public long getMemberid() {
		return memberid;
	}

	public void setMemberid(long memberid) {
		this.memberid = memberid;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	@Column("member.membername")
	private String membername;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
}
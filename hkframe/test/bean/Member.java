package bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "member")
public class Member {

	@Id
	private long userid;

	@Column
	private String nick;

	@Column
	private long groupid;

	private TestUser testUser;

	public void setTestUser(TestUser testUser) {
		this.testUser = testUser;
	}

	public TestUser getTestUser() {
		return testUser;
	}

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

	public long getGroupid() {
		return groupid;
	}

	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}
}

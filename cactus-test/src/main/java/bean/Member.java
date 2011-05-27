package bean;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

@Table(name = "member")
public class Member {

	@Id(name = "memberuserid")
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

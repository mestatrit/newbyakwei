package tuxiazi.bean;

public class LikeUser {

	private long userid;

	private String nick;

	public LikeUser() {
	}

	public LikeUser(long userid, String nick) {
		this.userid = userid;
		this.nick = nick;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}
}
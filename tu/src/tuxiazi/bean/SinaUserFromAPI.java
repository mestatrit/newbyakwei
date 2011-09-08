package tuxiazi.bean;

public class SinaUserFromAPI {

	public String token_secret;

	private String access_token;

	private long sinaUserId;

	private String nick;

	private String head;

	public SinaUserFromAPI(String access_token, String token_secret,
			long sinaUserId, String nick, String head) {
		this.access_token = access_token;
		this.token_secret = token_secret;
		this.sinaUserId = sinaUserId;
		this.nick = nick;
		this.head = head;
	}

	public long getSinaUserId() {
		return sinaUserId;
	}

	public void setSinaUserId(long sinaUserId) {
		this.sinaUserId = sinaUserId;
	}

	public String getToken_secret() {
		return token_secret;
	}

	public void setToken_secret(String token_secret) {
		this.token_secret = token_secret;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
}